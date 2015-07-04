from app import db

class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(64), index=True, unique=True)
    email = db.Column(db.String(120), index=True, unique=True)
    recipes = db.relationship('Recipe', backref='author', lazy='dynamic')


    def __repr__(self):
        return '<User %r>' % (self.name)

class Recipe(db.Model):
    id = db.Column(db.Integer, primary_key = True)
    action = db.Column(db.String(140))
    reaction = db.Column(db.String(140))
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'))

    def __repr__(self):
        return '<Recipe %r %r>' % (self.action, self.reaction)

class Action(db.Model):
    id = db.Column(db.Integer, primary_key = True)
    color = db.Column(db.String(7))
    name = db.Column(db.String(120))
    icon = db.Column(db.String(120))
    dataset = db.Column(db.String(120))
    act_details = db.Column(db.PickleType)

    def __repr__(self):
        return '<Action %r>' % (self.name)

