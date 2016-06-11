IPS-Server
==========

[Dickson College](dicksonc.act.edu.au) built an Indoor Positioning System (IPS) to track quadcopters and RC-cars indoors, with higher precision than GPS. The system uses Raspberry Pis and a colored marker on each vehicle to track them. Each vehicle is fitted with a Pixhawk autopilot and an Xbee connected to the Pixhawk's GPS input.

The source code running on the main ground computer talks to the Pis and the Pixhawks. It's job is to:

1. Listen to each node (running OpenCV on a Raspberry Pi)
2. Combine the pixel location of the vehicles with the relative location of the RPis & the global location of the server to determine the vehicle’s global location. 
3. Convert this into NMEA format
4. Using XBees, send it off the the Pixhawk autopilots.

The server is written using Python 2.7 and it relies on the XBee library and the GeoMag library (both included in the repo). The nodes, also running Python, use OpenCV to track the vehicles.

View IPS-Node software in action:

Preview 1: [Tracking a vehicle](http://gfycat.com/ArtisticWanIndianjackal) - ![Tracking gif](https://giant.gfycat.com/ArtisticWanIndianjackal.gif)

Preview 2: [We adapted the software to run on a UAV to track markers](http://gfycat.com/PeriodicArcticBanteng)


The on-ground hardware involved (including an autonomous system):
![Map](/IPS-Server/images/hardware.png?raw=true “Map”)

Here is the MissionPlanner software working together with our IPS:
![Map](/IPS-Server/images/map.png?raw=true “Map”)
