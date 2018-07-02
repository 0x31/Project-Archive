from flask import render_template, request
from app import app, models, db
import json
import requests
import os

@app.route('/new_recipe', methods=['GET','POST'])


def new_recipe():

    user_id = 1

    user = models.User.query.get(user_id)

    if request.method == 'POST':
        action = request.form['action']
        if(action=="no"):
            ac_id = request.form['ac_id']
            get_ac = models.Action.query.get(ac_id)
            db_id = get_ac.dataset
            get_db = models.Database.query.get(db_id)
            cols = ",".join(get_db.columns)
            return cols
        reaction = request.form['reaction']
        template = request.form['template']
        p = models.Recipe(action=action,reaction=reaction,template=template,author=user)
        db.session.add(p)
        db.session.commit()
        return action + reaction

    actions={}
    action_list = []
    action_db = models.Action.query.all()
    for action in action_db:
        action_list.append(action.id)
        actions[action.id]={"name": action.name, "icon":action.icon, "color":action.color, "id": action.id}

    reactions = {
            "email": { "name": "email me", "icon": "email.svg", "color":"#9DC241", "id":1},
            "sms": { "name": "send me an SMS (WIP)", "icon": "sms.svg", "color":"#2B8175", "id":2},
            "facebook": { "name": "post it to my wall (WIP)", "icon": "facebook.svg", "color":"#3b5998", "id":3},
            "twitter": { "name": "tweet it (WIP)", "icon": "twitter.svg", "color":"#4099FF", "id":4},
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
                            title="Create New Reaction",
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


    templates=[ {"name":"repeat","icon":"logo.svg","desc":"random row"},
            {"name":"distance","icon":"arrow.svg","desc":"distance trigger"},
            {"name":"value","icon":"arrow.svg","desc":"threshold trigger (WIP)"},
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
            if(url[-3:]=="csv"):
                labels = r.text.splitlines()[0].split(",")
                row1 = r.text.splitlines()[1].split(",")
                json_d = {}
                for ix in range(len(labels)):
                    json_d[labels[ix]]=row1[ix]
                first_row = json.dumps(json_d)
            else:
                rows = r.json()
                if isinstance(rows, dict):
                    max_key = ""
                    max_no  = 0
                    for key in rows:
                        if isinstance(rows[key],list) and len(rows[key])>max_no:
                            max_key=key
                            max_no=len(rows[key])
                    if max_key!="":
                        rows=rows[max_key]
                final={}
                for key1 in rows[0]:
                    if isinstance(rows[0][key1], dict):
                        for key2 in rows[0][key1]:
                            final[key1+"."+key2]=rows[0][key1][key2]
                    else:
                        final[key1]=rows[0][key1]
                first_row = json.dumps(final)
            return first_row
        else:
            name=request.form["name"]
            icon=request.form["icon"]
            color=request.form["color"]
            url=request.form["url1"]
            date=request.form["date"]
            longitude=request.form["longitude"]
            latitude=request.form["latitude"]
            columns=request.form["columns"].split(',')
            p = models.Database(name=name,icon=icon,url=url,color=color,date=date,longitude=longitude,latitude=latitude,columns=columns)
            db.session.add(p)
            db.session.commit()
            return ""

    colors=["#C74350","#38518A","#CEC745","#CEA045","#9F367A","#4EAC3A","#CE7E45","#8C2F84","#52398D"]
    icons=[]
    for file in os.listdir("app/static/img"):
        if file.endswith(".svg"):
            icons.append(file)

    return render_template( 'new_dataset.html',
                            colors=colors,
                            icons=icons,
                            title="Import Dataset")
