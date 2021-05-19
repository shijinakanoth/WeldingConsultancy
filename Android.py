from flask import Flask,render_template,request
import datetime,demjson
from DBConnection import Db

app = Flask(__name__)


@app.route('/registration',methods=['post'])
def registeration():
    name=request.form['editText4']
    email=request.form['editText6']
    contact=request.form['edittext2']
    address=request.form['editText10']
    password=request.form['editText11']
    db=Db()
    qry = db.insert("insert into login VALUES('','" + email + "','" + password+ "','client')")
    res=db.insert("insert into client VALUES ('"+str(qry)+"','"+name+"','"+email+"','"+address+"','"+contact+"')")
    result={}
    result["status"]="ok"
    return demjson.encode(result)



@app.route('/login_post',methods=['post'])
def login():
    username=request.form['username']
    password=request.form['password']
    db=Db()
    res={}
    qry=db.selectOne("select * from login where username='"+username+"' and password='"+password+"'")
    if qry is None:
        res['status']="Not a valid user"
    else:
        res['lid']=qry['logID']
        res['usertype']=qry['type']
        res['status'] = "ok"
    return  demjson.encode(res)


@app.route('/view_service',methods=['post'])
def viewservice():
    db=Db()
    res={}
    qry=db.select("select * from service WHERE status='1'")
    if len(qry)==0:
        res['status']="Sorry"
    else:
        res['data']=qry
        res['status']="ok"
    return demjson.encode(res)

@app.route('/uploadwork',methods=['post'])
def uploadwork():
    serid=request.form['serviceid']
    clientid=request.form['clientid']
    worksite=request.form['worksite']
    lati=request.form['latitude']
    longi=request.form['longitude']
    descr=request.form['description']
    db=Db()
    qry=db.insert("insert into worktable VALUES ('','"+serid+"','"+clientid+"','"+worksite+"','"+lati+"','"+longi+"',curdate(),curtime(),'pending','"+descr+"')")
    result = {}
    result["status"] = "ok"
    return demjson.encode(result)

@app.route('/workstatus',methods=['post'])
def workstatus():
    db=Db()
    lid=request.form['lid']
    res={}
    qry=db.select("select service.service,worktable.worksite,worktable.description,worktable.work_date,worktable.work_status,worktable.workid from client,service,worktable WHERE  worktable.serviceid=service.service_id and worktable.clientid=client.client_id and client.client_id='"+lid+"'")
    if len(qry)==0:
        res['status']="Sorry"
    else:
        res['data']=qry
        res['status']="ok"
    return demjson.encode(res)

@app.route('/payment',methods=['post'])
def payment():
    db=Db()
    # lid=request.form['lid']
    workid=request.form['workid']
    qry1=db.selectOne("select * from payment where status='paid' and workid='"+str(workid)+"' ")
    res={}
    if qry1 is not None:
        res['status'] = "Oops! Already paid"
    else:
        qry=db.update("update payment set status='paid' WHERE workid='"+workid+"'")
        res['status']="OK"
    return demjson.encode(res)



@app.route('/schedule',methods=['post'])
def schedule():
    db=Db()
    lid=request.form['lid']
    workid=request.form['workid']
    res={}
    qry=db.selectOne("select * from schedule,worktable,payment,client WHERE  worktable.workid=schedule.workid and worktable.clientid='"+lid+"'and worktable.workid='"+workid+"' and payment.workid=worktable.workid  and worktable.clientid=client.client_id ")
    if qry is not None:
        res['data'] = qry
        res['status'] = "ok"

    else:
        res['status'] = "Sorry"
    return demjson.encode(res)





@app.route('/send_complaint',methods=['post'])
def send_complaint():
    db = Db()
    lid = request.form['lid']
    wid = request.form['workid']
    com=request.form['complaint']
    q = db.insert("insert into complaint values ('','"+str(wid)+"','"+com+"',curdate(),'pending','pending','"+str(lid)+"')")
    res={}
    res['status']="ok"
    return demjson.encode(res)


@app.route('/view_complaint',methods=['post'])
def view_complaint():
    db = Db()
    q = db.select("select * from complaint,worktable,client WHERE  worktable.workid=complaint.workid and complaint.client_id=client.client_id")
    res={}
    res['status']="ok"
    res['reply']=q
    return demjson.encode(res)






@app.route('/add_rating',methods=['post'])
def add_rating():
    db = Db()
    lid = request.form['lid']
    rating=request.form['rating']
    review = request.form['review']
    q = db.insert("insert into review values ('','"+str(lid)+"','"+str(review)+"','"+str(rating)+"',now())")
    res={}
    res['status']="ok"
    return demjson.encode(res)


@app.route('/view_rating',methods=['post'])
def view_rating():
    db = Db()
    q = db.select("select * from review,client WHERE review.client_id=client.client_id")
    res={}
    res['status']="ok"
    res['data']=q
    return demjson.encode(res)




@app.route('/send_leave_req',methods=['post'])
def send_leave_req():
    db = Db()
    lid = request.form['lid']
    date=request.form['date']
    days = request.form['days']
    reason = request.form['reason']
    q = db.insert("insert into worker_leave values ('','"+str(lid)+"','"+str(date)+"','"+str(days)+"','"+str(reason)+"','pending',now())")
    res={}
    res['status']="ok"
    return demjson.encode(res)


@app.route('/view_leave_status',methods=['post'])
def view_leave_status():
    db = Db()
    lid = request.form['lid']
    q = db.select("select * from worker_leave,worker WHERE worker.workerid=worker_leave.worker_id and worker_leave.worker_id='"+str(lid)+"' order by apply_date desc")
    res={}
    res['status']="ok"
    res['leave']=q
    return demjson.encode(res)




@app.route('/view_profile',methods=['post'])
def view_profile():
    db = Db()
    lid=request.form['lid']
    q = db.selectOne("select * from client WHERE  client_id='"+str(lid)+"' ")
    res={}
    res['status']="ok"
    res['data']=q
    return demjson.encode(res)


@app.route('/attendence_mark',methods=['post'])
def attendence_mark():
    db = Db()
    lid=request.form['lid']
    workid=request.form['work_id']
    q = db.insert("insert into attendance values('','"+str(lid)+"','"+str(workid)+"',curdate())")
    res={}
    res['status']="ok"
    res['data']=q
    return demjson.encode(res)


if __name__ == '__main__':
    app.run(host='0.0.0.0')
