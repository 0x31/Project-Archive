#!/usr/bin/python
# -*- coding: utf-8 -*-

""" libxbee """

import serial

__author__ = '***REMOVED***'
__email__ = '***REMOVED***'

# Connect to Xbee
self.ser = serial.Serial(20, 9600, timeout=100)

# Send data (a string)
self.ser.write(packet)

# Read data
self.data += self.ser.read()
