from flask import render_template
from app import app

@app.route('/')
@app.route('/index')

def index():
    user = {'nickname': '***REMOVED***'}
    actions = {
            "fire": { "name": "there is a fire within 10km of me", "icon": "fire.svg", "color":"#C74350"},
            "recycle": { "name": "something about recyling", "icon": "logo.svg","color":"#9F367A"},
            "animals": { "name": "theres a new daily animal fact", "icon": "animal.svg", "color":"#C74350"},
            }

    reactions = {
            "email": { "name": "email me", "icon": "email.svg", "color":"#9DC241"},
            "sms": { "name": "send me an SMS", "icon": "sms.svg", "color":"#2B8175"},
            }

    my_recipes = [
            { "action": "fire", "reaction": "email", "id": 0 },
            { "action": "recycle", "reaction": "sms", "id": 2 },
            { "action": "recycle", "reaction": "email", "id": 1 },
            ]

    return render_template( 'index.html',
                            title="Recipes",
                            user=user,
                            recipes=my_recipes,
                            actions=actions,
                            reactions=reactions)

