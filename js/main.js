
Handlebars.registerHelper('loop', function(items, options) {
  var out = "";
  for(var i=0, l=items.length; i<l; i++) {out = out + options.fn(items[i]);}
  return out;
});


$(window).scroll(function() {          // Scroll listener
  checkScroll(true);
});

function checkScroll(animate) {
    var winPos = $(window).scrollTop(); // Get how much of the window is scrolled
    if (winPos > 191) {                // if it is over 75px (the initial width
      if(animate){
        $('.dash-hidden').slideDown('fast');
      }
      else {
        $('.dash-hidden').show();
      }
    } else {                         //else
        $('.dash-hidden').hide();
    }
}


function createCal() {
  var comp = new ICAL.Component(['vcalendar', [], []]);
  return comp;
}


function loadIcal() {

  var vcal = createCal();
  data = {'class':'COMP1140', 'name':'Assignment 1', 'start':'2015-07-28', 'end':'2015-08-02', 'color':'blue'}
  var uid1 = addTodo(vcal, data);
  var task1 = getTodo(vcal, uid1);
  data = {'name':'Subbie 2', 'date':'2015-07-31', 'checked': 'checked'}
  addSubtask(vcal, uid1, data);
  data = {'name':'Subbie 1', 'date':'2015-08-01', 'checked': ''}
  addSubtask(vcal, uid1, data);

  return vcal
}

function fs_save(vcal) {
  localStorage.setItem("vcal.ics", vcal.toString());
}

function fs_open() {
  var vcal = localStorage.getItem("vcal.ics");
  if(vcal===null || vcal.length===0){
    vcal = loadIcal();
    fs_save(vcal);
  }
  else {
    vcal = new ICAL.Component(ICAL.parse(vcal));
  }
  return vcal;
}

function fs_clear() {
  localStorage.removeItem("vcal.ics");
}


function addTodo(vcal, data){



  var vevent = new ICAL.Component('vtodo'),
      event = new ICAL.Event(vevent);

  var now = ICAL.Time.now();
  var uid = (data.class+"00000000").substring(0,8) + now.toString().replace(/\D/g,'').substring(6,12);

  event._setProp('summary',     data.name);
  event._setProp('class',       data.class);
  event._setProp('dtstamp',     now); // Can give it instances of appropriate types
  event._setProp('dtstart',     ICAL.Time.fromDateString(data.start)); // Can give it instances of appropriate types
  event._setProp('dtend',       ICAL.Time.fromDateString(data.end)); // Can give it instances of appropriate types
  event._setProp('uid',         uid);
  event._setProp('comment',     data.color);
  event._setProp('status',      "");
  event._setProp('description', "README");
  vcal.addSubcomponent(vevent);

  return uid;
}

function getTodo(vcal, uid){
  var todos = vcal.getAllSubcomponents("vtodo");
  for(var i=0; i<todos.length; i++){
    if (todos[i].getFirstPropertyValue("uid") == uid) {
      return todos[i];
    }
  }
  return null;
}

function getSubtask(todo, name){
  var todos = todo.getAllSubcomponents("vtodo");
  for(var i=0; i<todos.length; i++){
    if (todos[i].getFirstPropertyValue("summary") == name) {
      return todos[i];
    }
  }
  return null;
}

function addSubtask(vcal, uid, data){

  var todo = getTodo(vcal, uid);

  var vevent = new ICAL.Component('vtodo'),
      event = new ICAL.Event(vevent);

  var now = ICAL.Time.now();
  var uid1 = (data.class+"00000000").substring(0,8) + now.toString().replace(/\D/g,'').substring(6,12);

  event._setProp('summary',   data.name);
  event._setProp('dtstamp',   now); // Can give it instances of appropriate types
  event._setProp('dtend',     ICAL.Time.fromJSDate(data.date)); // Can give it instances of appropriate types
  event._setProp('status',    data.checked); // Can give it instances of appropriate types
  event._setProp('uid',       uid1);
  todo.addSubcomponent(vevent);

  renderTodo(uid, false);

  return uid;
}

function updateTodo(vcal, uid, name, value) {
  var vtodo = getTodo(vcal, uid);
  vtodo.updatePropertyWithValue(name, value);
  fs_save(vcal);
}

function updateSubtask(vcal, uid, taskname, name, value) {
  var todo = getTodo(vcal, uid);
  var vtodo = getSubtask(todo, taskname);
  vtodo.updatePropertyWithValue(name, value);
  fs_save(vcal);
}

function deleteSubtask(vcal, uid, taskname) {
  var todo = getTodo(vcal, uid);
  var vtodo = getSubtask(todo, taskname);
  todo.removeSubcomponent(vtodo);
  fs_save(vcal);
}



var vcal;
var template;
var uidcurrent;
function renderTodo(uid, different) {

  uidcurrent = uid;

  var tasks = [];

  var vtodo = getTodo(vcal, uid);

  var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
      "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    ];

  var start = vtodo.getFirstPropertyValue("dtstart");
  var end   = vtodo.getFirstPropertyValue("dtend");
  var dtdate = end.toJSDate().getDate().toString();
  var dtdatestart = start.toJSDate().getDate().toString();
  if(dtdate.length==1){
    dtdate="0"+dtdate;
  }
  var dtmonth = monthNames[end.toJSDate().getMonth()];
  var dtmonthstart = monthNames[start.toJSDate().getMonth()];
  var name  = vtodo.getFirstPropertyValue("summary");
  var nameid= name.replace(/\W/g, '');
  var clas  = vtodo.getFirstPropertyValue("class");
  var color = vtodo.getFirstPropertyValue("comment");

  var daysTot = start.subtractDate(end).toSeconds();
  var today = ICAL.Time.now();
  var daysRem = today.subtractDate(end).days * -1 * today.compare(end);
  var progress = ~~(99 * start.subtractDate(today).toSeconds() / daysTot);
  if(daysRem<0){
    progress = 100;
  }

  var subs = vtodo.getAllSubcomponents();

  for(var i=0;i<subs.length;i++){

    var subdate = subs[i].getFirstPropertyValue("dtend");
    var subname    = subs[i].getFirstPropertyValue("summary");
    var subnameid  = subname.replace(/\W/g, '')+'-'+i;
    var checked    = subs[i].getFirstPropertyValue("status");
    var subdateH = subdate.toJSDate().getDate() + " " + monthNames[subdate.toJSDate().getMonth()];
    var percentage = ~~( 99 * (subdate.subtractDate(start).toSeconds())*-1 / daysTot);
    //if(component.jCal[1][2][3]=="true"){
    //  checked="checked";
   // }
    

    tasks.push({done:checked, color:color, percentage:percentage, taskname:subname,
                taskdate:subdateH, id:subnameid, taskdateICAL: subdate});


  }

  tasks.sort(function(a, b) { 
    var ret = a.taskdateICAL.compare(b.taskdateICAL);
    return ret;
  })


  /*var tasks = [
      {done: "checked", color:name, percentage:"10",taskname:"Help1 or not",taskdate:"19 Nov"},
      {done: "", color:name, percentage:"60",taskname:"Help2",taskdate:"20 Nov"},
    ]*/

  daysL = daysRem.toString();
  if(daysL.length==1){
    daysL = "0" + daysL;
  }

  var context = { class: clas,
                  name: name,
                  dueno: daysL,
                  readme: "http://www.jeditoolkit.com/prose/",
                  color: color,
                  tasks: tasks,
                  day: dtdate,
                  month: dtmonth,
                  daystart: dtdatestart,
                  monthstart: dtmonthstart,
                  progress:progress };
  var html    = template(context);

  $("#right").html(html);
  checkScroll(false);

  if(different) {
    window.scrollTo(0,0);
  }



  for(var i=0;i<tasks.length;i++){
    var subname    = tasks[i].id;
    $('#field-'+subname).datepicker({});

    $("#field-"+subname)[0].oninput = function () {
      subname = this.getAttribute('id').substring(6);
      var nname = $("#text-"+subname).val();
      var ndate = $("#field-"+subname).val();
      var ddate = new Date(Date.parse(ndate+", 2015"));
      data = {'name':nname, 'date':ddate, 'checked': ''}
      updateSubtask(vcal, uidcurrent, nname, "dtend", ICAL.Time.fromJSDate(ddate).toString());
      fs_save(vcal);
      renderTodo(uidcurrent, false);
    };

    $("#text-"+subname).keyup( function(e) {
      if (e.which == 13) this.blur();
    });


    $("#text-"+subname).blur( function() {
      subname = this.getAttribute('id').substring(5);
      var nname = $("#text-"+subname).attr('alt');
      var ddate = $("#text-"+subname).val();
      if(ddate==""){
        deleteSubtask(vcal, uidcurrent, nname);
        fs_save(vcal);
        renderTodo(uidcurrent, false);
        return;
      }
      updateSubtask(vcal, uidcurrent, nname, "summary", ddate);
      fs_save(vcal);
      renderTodo(uidcurrent, false);
    });

    $('#check-'+subname).change(function(){
      var c = this.checked ? "checked" : "";

      subname = this.getAttribute('id').substring(6);
      var nname = $("#text-"+subname).attr('alt');
      updateSubtask(vcal, uidcurrent, nname, "status", c);
      fs_save(vcal);
      renderTodo(uidcurrent, false);
    });

  }
  $('#field').datepicker({});

  $("#field")[0].oninput = function () {
    var nname = $("#todo-add-name").val();
    var ndate = $("#field").val();
    var ddate = new Date(Date.parse(ndate+", 2015"));

    data = {'name':nname, 'date':ddate, 'checked': ''}
    addSubtask(vcal, uid, data);
    fs_save(vcal);
  };

  $("#todo-add-name").keyup( function() {
    if($("#todo-add-name").val().length>0){
      $("#field").css('visibility', 'visible');
    }
    else {
      $("#field").css('visibility', 'hidden');
    }
  });

  $("#edit-class").blur( function() {updateTodo(vcal, uidcurrent, "class", $(this).val()); renderTodo(uidcurrent, false);renderSide(vcal);});
  $("#edit-class").keyup( function(e) {if (e.which == 13) this.blur();});

  $("#edit-name").blur( function() {updateTodo(vcal, uidcurrent, "summary", $(this).val()); renderTodo(uidcurrent, false);renderSide(vcal);});
  $("#edit-name").keyup( function(e) {if (e.which == 13) this.blur();});

  $("#edit-color").blur( function() {updateTodo(vcal, uidcurrent, "comment", $(this).val()); renderTodo(uidcurrent, false);renderSide(vcal);});
  $("#edit-color").keyup( function(e) {if (e.which == 13) this.blur();});

  $("#edit-start").blur( function() { var dd =  new Date($(this).val()+"2015");
    updateTodo(vcal, uidcurrent, "dtstart", ICAL.Time.fromJSDate( dd ).toString()); renderTodo(uidcurrent, false);renderSide(vcal);});
  $("#edit-color").keyup( function(e) {if (e.which == 13) this.blur();});

  $("#edit-end").blur( function() { var dd =  new Date($(this).val()+"2015");
    updateTodo(vcal, uidcurrent, "dtend", ICAL.Time.fromJSDate( dd ).toString()); renderTodo(uidcurrent, false);renderSide(vcal);});
  $("#edit-end").keyup( function(e) {if (e.which == 13) this.blur();});

  
  return 0;
}



function renderSide(vcal) {
  $("#left-list").html("");
  var todos = vcal.getAllSubcomponents("vtodo");

  var overdue   = ["<span style='color: #000'>Overdue</span>"]
  var todays    = ["Due today"]
  var weeksies  = ["Due this week"]
  var monthsies = ["Due this month"]
  var moars     = ["Due after this month"]

  var all = [overdue, todays, weeksies, monthsies, moars]

  for(var i=0;i<todos.length;i++){
    var todo = todos[i]
    var dtdate = todo.getFirstPropertyValue("dtend");
    var now = ICAL.Time.now();
    var when = now.subtractDate(dtdate).days * now.compare(dtdate) * -1;

    if(when<0){
      overdue.push(todo);
    }
    else if(when==0){
      todays.push(todo);
    }
    else if(when<8){
      weeksies.push(todo);
    }
    else if(when<32){
      monthsies.push(todo);
    }
    else {
      moars.push(todo);
    }

  }

    var first = true;
    for(var i=0;i<all.length;i++){
      if(all[i].length>1){
        if(first){
          $("#left-list").append('<div><span class="date date1">'+all[i][0]+'</span>');
        }
        else {
          $("#left-list").append('<div><span class="date">'+all[i][0]+'</span>');
        }

        first = false;


        for(var j=1;j<all[i].length;j++){

          var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
              "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
            ];


          var todo = all[i][j];
          name = todo.getFirstPropertyValue("summary");
          uid  = todo.getFirstPropertyValue("uid");
          clas  = todo.getFirstPropertyValue("class");
          color  = todo.getFirstPropertyValue("comment");
          date  = todo.getFirstPropertyValue("dtend");
          month = monthNames[date.toJSDate().getMonth()];
          day = date.toJSDate().getDate().toString();
          if(day.length==1){
            day="0"+day;
          }

          $("#left-list").append('<a onclick="renderTodo(\''+uid+'\', true);"><div class="ass" id="uid">'+
              '<div class="ass-icon"><div class="ass-img '+color+'">'+
                      '<div class="ass-day">'+day+'</div>'+
                      '<div class="ass-month">'+month+'</div>'+
              '</div></div>'+
              '<span class="ass-title">'+clas+'</span>'+
              '<span class="ass-desc">'+name+'</span>'+
            '</div></a>');
        }

        
        $("#left-list").append('</div>');
      }
    }




}

$( document ).ready(function() {


  var source   = $("#tmpl").html();
  template = Handlebars.compile(source);

  vcal = fs_open();
  //data = {'class':'COMP2610', 'name':'Assignment Over', 'start':'2015-07-22', 'end':'2015-07-29', 'color':'blue'}
  //var uid1 = addTodo(vcal, data);
  fs_save(vcal);

  var uid2 = vcal.getAllSubcomponents("vtodo")[0].getFirstPropertyValue("uid");

  updateTodo(vcal, uid2, "class", "COMP1110");

  renderSide(vcal);


  //var task1 = getTodo(vcal, uid2);

  //fs_save(vcal);

  //renderTodo(uid2);

});






