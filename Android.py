from flask import Flask,render_template,request
import datetime,demjson
from DBConnection import Db

app = Flask(__name__)


@app.route('/registeration')
def registeration():
    name=request.form['editText4']
    email=request.form['editText6']
    contact=request.form['editText2']
    address=request.form['editText10']
    password=request.form['editText11']
    confirm=request.form['editText12']
    db=Db()
    qry = db.insert("insert into login VALUES('','" + email + "','" + password+ "','worker')")
    res=db.insert("insert into client VALUES ('"+name+"','"+email+"','"+contact+"','"+address+"')")



@app.route('/login_post',methods=['post'])
def login():
    username=request.form['username']
    password=request.form['password']
    db=Db()
    res={}
    qry=db.selectOne("select * from login where username='"+username+"' and passsword='"+password+"'")
    if qry is None:
        res['status']="none"
    else:
        res['lid']=qry['logID']
        res['usertype']=qry['type']
        res['status'] = "ok"
    return  demjson

















if __name__ == '__main__':
    app.run()
