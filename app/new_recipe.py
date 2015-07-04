from flask import render_template, request
from app import app, models, db
import json
import requests

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






@app.route("/new_action", methods=['GET','POST'])
def new_action():


    user_id = 1

    user = models.User.query.get(user_id)

    if request.method == 'POST':
        color = request.form['color']
        name = request.form['name']
        dataset = request.form['dataset']
        act_details = json.loads(request.form['act_details'])
        icon = request.form['icon']
        p = models.Action(color=color,name=name,dataset=dataset,icon=icon,act_details=act_details)
        db.session.add(p)
        db.session.commit()
        return ""


    templates=[ {"name":"repeat","icon":"logo.svg","desc":"timed trigger"},
            {"name":"distance","icon":"arrow.svg","desc":"distance trigger"},
            {"name":"value","icon":"arrow.svg","desc":"threshold trigger"},
            {"name":"updated","icon":"new.svg","desc":"updated data trigger"}]
    #databases=[{"name":"sentinel","url":"http","desc":"sentinel","icon":"fire.svg"}]

    dbs = models.Database.query.all()
    databases = []
    for dbl in dbs:
        databases += [{"name":dbl.id,"icon":dbl.icon,"url":dbl.url,"columns":dbl.columns,"desc":dbl.name,"color":dbl.color}]

    return render_template( 'new_action.html',
                            title="Create New Action",
                            templates=templates,
                            databases=databases)




@app.route("/new_dataset", methods=['GET','POST'])
def new_dataset():

    user_id = 1

    user = models.User.query.get(user_id)

    if request.method == 'POST':
        if(request.form["url"]!="no"):
            url=request.form["url"];
            r = requests.get(
                url,
                headers={}
            )
            return json.dumps(r.json()[0])
        else:
            name=request.form["name"]
            icon=request.form["icon"]
            color=request.form["color"]
            url=request.form["url1"]
            columns=json.loads(request.form["columns"])
            p = models.Database(name=name,icon=icon,url=url,color=color,columns=columns)
            db.session.add(p)
            db.session.commit()
            return ""

    colors=["#2B8175","rgb(199, 67, 80)","#9DC241","#38518A","red","blue","yellow"]
    icons=["fire.svg","new.svg","logo.svg", "animal.svg", "email.svg", "flask.svg", "sms.svg"]

    return render_template( 'new_dataset.html',
                            colors=colors,
                            icons=icons,
                            title="Import Dataset")
