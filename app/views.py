from flask import render_template
from app import app, models

@app.route('/')
@app.route('/index')


def index():
    user = {'nickname': '***REMOVED***'}

    actions={}
    action_list = []
    action_db = models.Action.query.all()
    for action in action_db:
        action_list.append(str(action.id))
        actions[str(action.id)]={"name": action.name, "icon":action.icon, "color":action.color, "id": str(action.id)}

    reactions = {
            "email": { "name": "email me", "icon": "email.svg", "color":"#9DC241", "id":1},
            "sms": { "name": "send me an SMS", "icon": "sms.svg", "color":"#2B8175", "id":2},
            "facebook": { "name": "post it to my wall", "icon": "facebook.svg", "color":"#3b5998", "id":3},
            "twitter": { "name": "tweet it", "icon": "twitter.svg", "color":"#4099FF", "id":4},
            }

    my_recipes = [
            { "action": "fire", "reaction": "email", "id": 0 },
            { "action": "recycle", "reaction": "sms", "id": 2 },
            { "action": "recycle", "reaction": "email", "id": 1 },
            ]

    user_id = 1

    user = models.User.query.get(user_id)
    recipes = user.recipes.all()
    return_list = []
    for recipe in recipes:
        return_list += [{"action":recipe.action,"reaction":recipe.reaction}]
        #stl = recipe.action
    #return_list = [{"action":"fire","reaction":"email"}]


    return render_template( 'index.html',
                            title="Reactions",
                            user=user,
                            recipes=return_list,
                            actions=actions,
                            reactions=reactions)


@app.route("/about")
def about():
    return render_template( 'about.html',title="About" )


