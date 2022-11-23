import csv
import sys
with open('dataset.csv', 'rb') as f:
  reader = csv.reader(f)
  previous=""
  for row in reader:
    if(row[0]!=""):
      previous=row[0]
    if row[0]=="Country" or (row[1]=="1995" and ((row[2]=="15" and row[3]=="+" and False) or (row[2]=="15" and row[3]=="+"))):
      st = ""
      i=0
      for cell in row:
        if(cell==""):
          cell=previous
        if(i in [6,8]):
          cell = cell[0:]
          cell = cell.replace(",","")
          st+= ("%s," % cell)
        i+=1
      st=st[:-1]
      print st
