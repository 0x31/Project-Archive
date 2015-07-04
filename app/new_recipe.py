from flask import render_template, request
from app import app, models, db

@app.route('/new_recipe', methods=['GET','POST'])


def new_recipe():
    user_id = 1

    user = models.User.query.get(user_id)

    if request.method == 'POST':
        action = request.form['action']
        reaction = request.form['reaction']
        p = models.Recipe(action=action,reaction=reaction,author=user)
        db.session.add(p)
        db.session.commit()
        return action + reaction

    #user = {'nickname': '***REMOVED***'}

    actions={}
    action_list = []
    action_db = models.Action.query.all()
    for action in action_db:
        action_list.append(action.id)
        actions[action.id]={"name": action.name, "icon":action.icon, "color":action.color, "id": action.id}

    reactions = {
            "email": { "name": "email me", "icon": "email.svg", "color":"#9DC241", "id":0},
            "sms": { "name": "send me an SMS", "icon": "sms.svg", "color":"#2B8175", "id":1},
            }

    reaction_list = []
    for reaction in reactions:
        reaction_list.append(reaction)


    user = models.User.query.get(user_id)
    recipes = user.recipes.all()
    return_list = []
    for recipe in recipes:
        return_list += [{"action":recipe.action,"reaction":recipe.reaction}]
        #stl = recipe.action
    #return_list = [{"action":"fire","reaction":"email"}]


    return render_template( 'new_recipe.html',
                            title="Create New Recipe",
                            user=user,
                            recipes=return_list,
                            actions=actions,
                            reactions=reactions,
                            action_list = action_list,
                            reaction_list = reaction_list)






@app.route("/new_action")
def new_action():
    return "New thingy created"
