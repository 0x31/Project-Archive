
Handlebars.registerHelper('loop', function(items, options) {
  var out = "";

  for(var i=0, l=items.length; i<l; i++) {
    out = out + options.fn(items[i]);
  }

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


var comp;
function createCal() {
  comp = new ICAL.Component(['vcalendar', [], []]);
}
createCal();


function createTask() {

  var vtodo =
    "BEGIN:VCALENDAR\n"+
      "BEGIN:VTODO\n"+
        "SUMMARY:{{name}}\n"+
        "CLASS:{{class}}\n"+
        "DTSTAMP:{{now}}\n"+
        "DTSTART,VALUE=DATE:{{start}}\n"+
        "DTEND,VALUE=DATE:{{end}}\n"+
        "UID:{{uid}}\n"+
        "{{#loop tasks}}\n"+
          "BEGIN:VTODO\n"+
            "SUMMARY:{{subname}}\n"+
            "DTEND,VALUE=DATE:{{subend}}\n"+
            "DONE:{{done}}\n"+
          "END:VTODO\n"+
        "{{/loop}}\n"+
      "END:VTODO\n"+
    "END:VCALENDAR\n"

  var template1 = Handlebars.compile(vtodo);

  uid = ("COMP1140"+"00000000").substring(0,8) + ICAL.Time.now().toString().replace(/\D/g,'');

  var tasks = [
      {subname:"Subtask 1",subend:"20150725",done:"true"},
      {subname:"Subtask 2 Part B",subend:"20150728",done:"false"},
    ]
  
  
  var context = {class: "COMP1140", name: "Assignment 2", now: ICAL.Time.now(),
                 start: "20150722", end: "20150801", uid: uid, tasks:tasks};
  var html    = template1(context);

  return html

}
comp = createTask();
//alert(comp);






var jcalData = ICAL.parse(comp);
var comp = new ICAL.Component(jcalData);
var vevent = comp.getAllSubcomponents("vtodo")[0];
var event2 = new ICAL.Event(vevent);

function humanDate (date) {
    var month = parseInt(date.substring(4,6));
    var month_str = "JanFebMarAprMayJunJulAugSepOctNovDec".substring(month*3-3, month*3);
    var day = parseInt(date.substring(6,8));
    return (day + " " + month_str);
}

function dateDifference(date1, date2) {
  var day1 = parseInt(date1.substring(6,8));
  var mon1 = parseInt(date1.substring(4,6));
  var yea1 = parseInt(date1.substring(0,4));
  dt1 = new Date(yea1, mon1-1, day1);
  var day2 = parseInt(date2.substring(6,8));
  var mon2 = parseInt(date2.substring(4,6));
  var yea2 = parseInt(date2.substring(0,4));
  dt2 = new Date(yea2, mon2-1, day2);
  return (dt2 - dt1)/(1000*60*60*24);

}


Date.prototype.yyyymmdd = function() {
   var yyyy = this.getFullYear().toString();
   var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based
   var dd  = this.getDate().toString();
   return yyyy + (mm[1]?mm:"0"+mm[0]) + (dd[1]?dd:"0"+dd[0]); // padding
  };

var template;
function renderTodo(name) {

  var tasks = [];


  var start = event2.component.jCal[1][3][3];
  var end = event2.component.jCal[1][4][3];


  var vevent1 = vevent.getAllSubcomponents("vtodo");

  var daysTot = dateDifference(start, end);
  var today = new Date().yyyymmdd();
  var daysRem = dateDifference(today, end);
  var progress = ~~(99 * dateDifference(start, today) / daysTot);

  for(var i=0;i<vevent1.length;i++){
    var todo = new ICAL.Event(vevent1[i]);
    var subdate = todo.component.jCal[1][1][3];
    var checked="";
    var subdateH = humanDate(subdate);
    var percentage = ~~(99 * dateDifference(start, subdate) / daysTot);
    if(todo.component.jCal[1][2][3]=="true"){
      checked="checked";
    }
    

    tasks.push({done:checked, color:name, percentage:percentage, taskname:todo.summary,
                taskdate:subdateH});

  }




  /*var tasks = [
      {done: "checked", color:name, percentage:"10",taskname:"Help1 or not",taskdate:"19 Nov"},
      {done: "", color:name, percentage:"60",taskname:"Help2",taskdate:"20 Nov"},
    ]*/

  daysL = ("0" + daysRem.toString() ).substring(-1);

  var context = {class: "COMP1140", name: "Assignment 2", dueno: daysL, readme: "http://www.jeditoolkit.com/prose/", color: name, tasks: tasks, progress:progress };
  var html    = template(context);

  $("#right").html(html);
  checkScroll(false);
  window.scrollTo(0,0);

  
  return 0;
}

$( document ).ready(function() {
  var source   = $("#tmpl").html();
  template = Handlebars.compile(source);
  renderTodo("red");
});


