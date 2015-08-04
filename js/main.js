
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
  event._setProp('description', "http://www.jeditoolkit.com/prose/");
  vcal.addSubcomponent(vevent);

  return uid;
}

function triggerNew() {
  var today = new Date();
  var next = new Date();
  next.setDate(next.getDate() + 8);

  var color = Math.random() < 0.5 ? "red" : "blue";
  
  data = {'class':'NAME1115', 'name':'Assignment 1', 'start':today.format("yyyy-mm-dd"), 'end':next.format("yyyy-mm-dd"), 'color':color}
  var uid1 = addTodo(vcal, data);
  fs_save(vcal);
  renderTodo(uid1, true);
  renderSide(vcal);
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

function deleteTodo(vcal, uid) {
  var todo = getTodo(vcal, uid);
  vcal.removeSubcomponent(todo);
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
  var readme = vtodo.getFirstPropertyValue("description");
  var dtmonth = monthNames[end.toJSDate().getMonth()];
  var dtmonthstart = monthNames[start.toJSDate().getMonth()];
  var name  = vtodo.getFirstPropertyValue("summary");
  var nameid= name.replace(/\W/g, '');
  var clas  = vtodo.getFirstPropertyValue("class");
  var color = vtodo.getFirstPropertyValue("comment");

  var daysTot = start.subtractDate(end).toSeconds();
  var today = ICAL.Time.now();
  var daysRem = ~~(today.subtractDate(end).toSeconds() * -1 / (60*60*24));
  var progress = ~~(99 * start.subtractDate(today).toSeconds() / daysTot);
  if(daysRem<0){
    progress = 100;
  }

  var subs = vtodo.getAllSubcomponents();

  percentages = [];

  for(var i=0;i<subs.length;i++){

    var subdate = subs[i].getFirstPropertyValue("dtend");
    var subname    = subs[i].getFirstPropertyValue("summary");
    var subnameid  = subname.replace(/\W/g, '')+'-'+i;
    var checked    = subs[i].getFirstPropertyValue("status");
    var subdateH = subdate.toJSDate().getDate() + " " + monthNames[subdate.toJSDate().getMonth()];
    var percentage = ~~( 99 * (subdate.subtractDate(start).toSeconds())*-1 / daysTot);
    while(percentages.indexOf(percentage) >= 0) {
      percentage += 4;
    }
    percentages.push(percentage);
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
                  readme: readme,
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
      updateSubtask(vcal, uidcurrent, nname, "dtend", ICAL.Time.fromJSDate(ddate));
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

  $("#edit-name").blur( function() {
    if( $(this).val()=="" ) {
      deleteTodo( vcal, uidcurrent );
      $("#right").html("");
      renderSide(vcal);
      return;
    }
    updateTodo(vcal, uidcurrent, "summary", $(this).val()); renderTodo(uidcurrent, false);renderSide(vcal);});
  $("#edit-name").keyup( function(e) {if (e.which == 13) this.blur();});
  $("#edit-name").focusin( function() { $(this).attr("placeholder","Delete")});

  $("#edit-color").blur( function() {updateTodo(vcal, uidcurrent, "comment", $(this).val()); renderTodo(uidcurrent, false);renderSide(vcal);});
  $("#edit-color").keyup( function(e) {if (e.which == 13) this.blur();});

  $("#readme").blur( function() {updateTodo(vcal, uidcurrent, "description", $(this).val()); renderTodo(uidcurrent, false);renderSide(vcal);});
  $("#readme").keyup(function(e) {
    while($(this).outerHeight() < this.scrollHeight + parseFloat($(this).css("borderTopWidth")) + parseFloat($(this).css("borderBottomWidth"))) {
      $(this).height($(this).height()+1);
    };
  });
  $("#readme").keyup();

  $("#edit-start").blur( function() { var dd =  new Date($(this).val()+"2015");
    updateTodo(vcal, uidcurrent, "dtstart", ICAL.Time.fromJSDate( dd )); renderTodo(uidcurrent, false);renderSide(vcal);});
  $("#edit-start").keyup( function(e) {if (e.which == 13) this.blur();});

  $("#edit-end").blur( function() { var dd =  new Date($(this).val()+"2015");
    updateTodo(vcal, uidcurrent, "dtend", ICAL.Time.fromJSDate( dd )); renderTodo(uidcurrent, false);renderSide(vcal);});
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
    var when = now.subtractDate(dtdate).seconds * now.compare(dtdate) * -1;

    if(when<0){
      overdue.push(todo);
    }
    else if(when<30*60*25 && when>30*60*25){
      todays.push(todo);
    }
    else if(when<8 * 60*60*24){
      weeksies.push(todo);
    }
    else if(when<32* 60*60*24){
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

  renderSide(vcal);


  //var task1 = getTodo(vcal, uid2);

  //fs_save(vcal);

  //renderTodo(uid2);

});




var dateFormat = function () {
    var token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
        timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
        timezoneClip = /[^-+\dA-Z]/g,
        pad = function (val, len) {
            val = String(val);
            len = len || 2;
            while (val.length < len) val = "0" + val;
            return val;
        };

    // Regexes and supporting functions are cached through closure
    return function (date, mask, utc) {
        var dF = dateFormat;

        // You can't provide utc if you skip other args (use the "UTC:" mask prefix)
        if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
            mask = date;
            date = undefined;
        }

        // Passing date through Date applies Date.parse, if necessary
        date = date ? new Date(date) : new Date;
        if (isNaN(date)) throw SyntaxError("invalid date");

        mask = String(dF.masks[mask] || mask || dF.masks["default"]);

        // Allow setting the utc argument via the mask
        if (mask.slice(0, 4) == "UTC:") {
            mask = mask.slice(4);
            utc = true;
        }

        var _ = utc ? "getUTC" : "get",
            d = date[_ + "Date"](),
            D = date[_ + "Day"](),
            m = date[_ + "Month"](),
            y = date[_ + "FullYear"](),
            H = date[_ + "Hours"](),
            M = date[_ + "Minutes"](),
            s = date[_ + "Seconds"](),
            L = date[_ + "Milliseconds"](),
            o = utc ? 0 : date.getTimezoneOffset(),
            flags = {
                d:    d,
                dd:   pad(d),
                ddd:  dF.i18n.dayNames[D],
                dddd: dF.i18n.dayNames[D + 7],
                m:    m + 1,
                mm:   pad(m + 1),
                mmm:  dF.i18n.monthNames[m],
                mmmm: dF.i18n.monthNames[m + 12],
                yy:   String(y).slice(2),
                yyyy: y,
                h:    H % 12 || 12,
                hh:   pad(H % 12 || 12),
                H:    H,
                HH:   pad(H),
                M:    M,
                MM:   pad(M),
                s:    s,
                ss:   pad(s),
                l:    pad(L, 3),
                L:    pad(L > 99 ? Math.round(L / 10) : L),
                t:    H < 12 ? "a"  : "p",
                tt:   H < 12 ? "am" : "pm",
                T:    H < 12 ? "A"  : "P",
                TT:   H < 12 ? "AM" : "PM",
                Z:    utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
                o:    (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
                S:    ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
            };

        return mask.replace(token, function ($0) {
            return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
        });
    };
}();

// Some common format strings
dateFormat.masks = {
    "default":      "ddd mmm dd yyyy HH:MM:ss",
    shortDate:      "m/d/yy",
    mediumDate:     "mmm d, yyyy",
    longDate:       "mmmm d, yyyy",
    fullDate:       "dddd, mmmm d, yyyy",
    shortTime:      "h:MM TT",
    mediumTime:     "h:MM:ss TT",
    longTime:       "h:MM:ss TT Z",
    isoDate:        "yyyy-mm-dd",
    isoTime:        "HH:MM:ss",
    isoDateTime:    "yyyy-mm-dd'T'HH:MM:ss",
    isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
};

// Internationalization strings
dateFormat.i18n = {
    dayNames: [
        "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
        "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    ],
    monthNames: [
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
        "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
    ]
};

// For convenience...
Date.prototype.format = function (mask, utc) {
    return dateFormat(this, mask, utc);
};
