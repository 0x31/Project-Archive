

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





var iCalendarData = "BEGIN:VCALENDAR\n" +
  "CALSCALE:GREGORIAN\n" +
  "PRODID:-//Example Inc.//Example Calendar//EN\n" +
  "VERSION:2.0\n" +
  "BEGIN:VTODO\n" +
  "DTSTAMP:20080205T191224Z\n" +
  "DTSTART:20081006\n" +
  "DTEND;VALUE=DATE:20090727\n" +
  "SUMMARY:Planning meeting\n" +
  "DESCRIPTION:La di da di da\\nwoopdidu\n" +
  "UID:4088E990AD89CB3DBB484909\n" +
  "BEGIN:VTODO\n" +
  "SUMMARY:Planning meeting\n" +
  "DTSTART;VALUE=DATE:20081006\n" +
  "DTSTAMP:20080205T191224Z\n" +
  "DTEND;VALUE=DATE:20090727\n" +
  "END:VTODO\n" +
  "END:VTODO\n" +
  "END:VCALENDAR"
var jcalData = ICAL.parse(iCalendarData);

var comp = new ICAL.Component(jcalData);
var vevent = comp.getAllSubcomponents("vtodo");
var vevent1 = vevent[0].getAllSubcomponents("vtodo");
var summary = vevent1[0].getFirstPropertyValue("dtstart");
var date = summary.toJSDate();

Handlebars.registerHelper('loop', function(items, options) {
  var out = "";

  for(var i=0, l=items.length; i<l; i++) {
    out = out + options.fn(items[i]);
  }

  return out;
});


var template;
function renderTodo(name) {


  var tasks = [
      {done: "checked", color:name, percentage:"10",taskname:"Help1",taskdate:"19 Nov"},
      {done: "", color:name, percentage:"60",taskname:"Help2",taskdate:"20 Nov"},
    ]

  var context = {class: "COMP1140", name: "Assignment 2", dueno: "94", readme: "http://www.jeditoolkit.com/prose/", color: name, tasks: tasks};
  var html    = template(context);

  $("#right").html(html);
  checkScroll(false);
  window.scrollTo(0,0);

  
  return 0;
}

$( document ).ready(function() {
  var source   = $("#tmpl").html();
  template = Handlebars.compile(source);
});

