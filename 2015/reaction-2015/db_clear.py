#!flask/bin/python
from app import db, models

users = models.Recipe.query.all()
for u in users:
 db.session.delete(u)

users = models.Action.query.all()
for u in users:
 db.session.delete(u)

users = models.Database.query.all()
for u in users:
 db.session.delete(u)

db.session.commit()

