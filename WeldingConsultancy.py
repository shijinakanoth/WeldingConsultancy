from flask import Flask,render_template,request
import datetime
from DBConnection import Db
import qrcode
import random
app = Flask(__name__)
path="C:\\Users\\DELL\\PycharmProjects\\WeldingConsultancy\\static\\photos\\"


@app.route('/')
def home():
    return render_template("home_index.html")


@app.route('/log')
def adminlogin():
    return render_template("login_index.html")

@app.route('/login',methods=['post'])
def login():
    username=request.form['username']
    password=request.form['password']
    db=Db()
    qry=db.selectOne("select * from login where username='"+username+"' and password='"+password+"'")
    if qry is not None:
        if qry['type']=="admin":

            return render_template("Admin/admin_home.html")
        else:
            return'<script>alert("Logging in...." );window.location="/adminhome"</script>'



@app.route('/addworker')
def addworker():
    return render_template("Admin/add_worker.html")

@app.route('/addworker1',methods=['post'])
def addworker1():
    name=request.form['txt_name']
    dob=request.form['txt_dob']
    mobile=request.form['txt_mobile']
    adhar=request.form['txt_adhar']
    email=request.form['txt_email']
    house=request.form['txt_house']
    place=request.form['txt_place']
    post=request.form['txt_post']
    pin=request.form['txt_pin']
    photo=request.files['photo']
    date=datetime.datetime.now().strftime("%y%m%d-%H%M%S")
    photo.save(path+date+".jpg")
    wages=request.form['wages']
    pth="/static/photos/"+date+".jpg"
    db=Db()
    pwd=random.randint(0000,9999)
    qry=db.insert("insert into login VALUES('','"+email+"','"+str(pwd)+"','worker')")
    db.insert("insert into worker VALUES ('"+str(qry)+"','"+name+"','"+dob+"','"+mobile+"','"+adhar+"','"+email+"','"+house+"','"+place+"','"+post+"','"+ pin+"','"+pth+"','"+wages+"')")

    return '<script>alert("inserted successfully");window.location="/addworker"</script>'

@app.route('/addrawmaterial')
def addrawmaterial():
    return render_template("Admin/admin_addRawmaterial.html")

@app.route('/addrawmaterial1',methods=['post'])
def addrawmaterial1():
    rawname=request.form['raw_name']
    rawquantity=request.form['raw_quantity']
    descr=request.form['description']
    db1=Db()
    db1.insert("insert into rawmaterial VALUES ('','"+rawname+"','"+rawquantity+"','"+descr+"')")
    return '<script>alert("inserted...."); window.location="/addrawmaterial"</script>'

@app.route('/addreqrawmaterial')
def addreqrawmaterial():
    return render_template("Admin/admin_addrequiredRaw.html")

@app.route('/addreqrawmaterial1',methods=['post'])
def addreqrawmaterial1():
    reqname=request.form['txt_reqraw']
    reqquantity=request.form['txt_reqquantity']
    descr = request.form['description']
    db2=Db()
    db2.insert("insert into requiredrawmaterial VALUES ('','"++"','"++"')")

    return render_template("Admin/admin_addrequiredRaw.html")

@app.route('/addservice')
def addservice():
    return render_template("Admin/admin_addService.html")

@app.route('/addservice1',methods=['post'])
def addservice1():
    service=request.form['txt_service']
    description=request.form['txt_service1']
    db=Db()
    db.insert("insert into service VALUES ('','"+service+"','1','"+description+"')")
    return '<script>alert("Service added");window.location="/addservice"</script>'


@app.route('/confirmleave')
def confirmleave():
    db=Db()
    qry=db.select("select * from worker,worker_leave where worker.workerid=worker_leave.worker_id")
    return render_template("Admin/admin_confirmLeave.html",data=qry)



@app.route('/approve_leave/<id>')
def approve_leave(id):
    db=Db()
    qry=db.update("update worker_leave set status='approve' where leave_id='"+str(id)+"'")
    return '<script>alert("Approved");window.location="/confirmleave"</script>'



@app.route('/reject_leave/<id>')
def reject_leave(id):
    db=Db()
    qry=db.update("update worker_leave set status='reject' where leave_id='"+str(id)+"'")
    return '<script>alert("Rejected");window.location="/confirmleave"</script>'

@app.route('/confirmwork')
def confirmwork():
    db=Db()
    qry=db.select("select * from worktable,service,client where worktable.serviceid=service.service_id and worktable.clientid=client.client_id ")
    return render_template("Admin/admin_confirmWork.html",data=qry)

@app.route('/reject_work/<a>')
def reject_work(a):
    db=Db()
    db.update("update  worktable set work_status='reject' WHERE workid='"+a+"' ")
    return '<script>alert("Rejected");window.location="/confirmwork"</script>'

@app.route('/workdetails/<a>/<b>')
def workdetails(a,b):
    return render_template("Admin/WorkDetails.html",d=a,d1=b)


@app.route('/workdetails1/<a>/<b>',methods=['post'])
def workdetails1(a,b):
    db=Db()
    num=request.form['no']
    sdate=request.form['sdate']
    edate=request.form['edate']
    amt=request.form['amt']
    qr_code=qrcode.make(b)
    qr_code.save("E:\\welding\\WeldingConsultancy_python\\static\\qrcode\\"+str(b)+".png")
    db.insert("insert into payment VALUES ('','"+b+"','"+amt+"','','','pending')")
    db.insert("insert into schedule VALUES ('','"+b+"','"+sdate+"','"+edate+"','"+num+"')")
    db.update("update  worktable set work_status='confirm' WHERE workid='" + b + "' ")
    return '<script>alert("Added successfully");window.location="/confirmwork"</script>'

@app.route('/confirmwork1')
def confirmwork1():
    db=Db()
    qry=db.select("select * from worktable,service,client where worktable.serviceid=service.service_id and worktable.clientid=client.client_id and worktable.work_status!='pending'")
    return render_template("Admin/confirmed_works.html",data=qry)


@app.route('/update_work_status/<id>')
def update_work_status(id):
    db=Db()
    qry=db.selectOne("select * from worktable where workid='"+str(id)+"'")
    return  render_template("Admin/update_work_status.html",id=id,data=qry)

@app.route('/update_work_status_post/<id>',methods=['post'])
def update_work_status_post(id):
    s=request.form['txt_reply']
    db=Db()
    qry=db.update("update worktable set work_status='"+str(s)+"' where workid='"+str(id)+"' ")
    return '<script>alert("Status Updated");window.location="/confirmwork"</script>'

@app.route('/adminhome')
def adminhome():
    return render_template("Admin/admin_home.html")

@app.route('/paymenthistory')
def paymenthistory():
    db=Db()
    qry=db.select("select * from payment,worktable,client where payment.workid=worktable.workid and worktable.clientid=client.client_id")
    return render_template("Admin/admin_paymentHistory.html",data=qry)

@app.route('/updatestatus')
def updatestaus():
    db=Db()
    qyr=db.select("select * from worktable,client where worktable.clientid=client.client_id")
    return render_template("Admin/admin_updateWorkStatus.html",data=qyr)

@app.route('/status/<id>')
def status(id):
    db = Db()
    qry = db.selectOne("select * from worktable where workidid='" + id + "'")
    return render_template("Admin/Status.html",data=qry)



@app.route('/status1/<id>',methods=['post'])
def status1(id):
    db=Db()
    status=request.form['raw_name']
    qry=db.update("update worktable set status='"+status+"' where workid='"+id+"'")
    return updatestaus()




@app.route('/view_attendence/<id>',methods=['post'])
def view_attendence(id):
    b=request.form['s']
    db=Db()
    if b=="View Attendence":
        qry=db.select("select * from attendance,worker,worktable where worker.workerid=attendance.worker_id and attendance.work_id=worktable.workid order by attendance.date desc ")
        if len(qry)>0:
            return render_template("Admin/admin_viewAttendance.html",data=qry)
        else:
            return render_template("Admin/admin_viewAttendance.html", data=0)
    else:
        qry = db.selectOne("select * from worktable,payment,schedule where schedule.workid=worktable.workid and worktable.workid=payment.workid and payment.workid='" + str(id) + "'")
        amount = qry['amount']
        number = qry['number_workers']
        total = float(amount) * float(number)
        return render_template("Admin/payment.html", data=qry,total=total)


@app.route('/payment/<id>/<amount>',methods=['post'])
def payment(id,amount):
    db=Db()
    account=request.form['acc']
    ifc=request.form['i']
    total = request.form['textfield3']
    a = db.selectOne("select * from account where account_no='" + account + "' and ifsc_code='" + ifc + "'")
    o_account = db.selectOne("select * from account where account_id='2'")
    if a is not None:
        balance = a['balance']
        if float(balance) > float(total):
            available_balance = float(balance) - float(total)

            o_balance = o_account['balance']
            o_cbalance = float(o_balance) + float(total)
            db.update("update account set balance='" + str(available_balance) + "' where account_no='" + account + "'")

            db.update("update account set balance='" + str(o_cbalance) + "' where account_id='3'")
            db.update("update payment set status='salary_paid' where workid='" + str(id) + "'")
            qry2=db.select("select * from scheduleworker where scheduleworker.workid='"+str(id)+"'")
            for i in qry2:
                worker_id=i['worker_id']
                q = db.insert("insert into salary values ('','" + str(amount) + "','" + str(worker_id) + "',curdate())")
            return '<script>alert("Payment successfully");window.location="/confirmwork"</script>'
        else:
            return '<script>alert("Insufficent Balance");window.location="/confirmwork"</script>'
    else:
        return '<script>alert("Invalid Account");window.location="/confirmwork"</script>'


@app.route('/viewclient')
def viewclient():
    db=Db()
    qry=db.select("select * from client")
    return render_template("Admin/admin_viewClient.html",data=qry)

@app.route('/viewcomplaint')
def viewcomplaint():
    db=Db()
    qry=db.select("select * from complaint,worktable,client,service where complaint.workid=worktable.workid and worktable.clientid=client.client_id and  worktable.serviceid=service.service_id")
    return render_template("Admin/admin_viewComplaints.html",data=qry)


@app.route('/view_act/<w>')
def view_act(w):
    return render_template("Admin/admin_giveReply.html",x=w)



@app.route('/act_reply/<id>',methods=['POST'])
def act_reply(id):
    r=request.form["txt_reply"]
    db=Db()
    db.update("update complaint set reply_date=now(),reply='"+r+"' where complaint_id='"+str(id)+"'")
    return viewcomplaint()

@app.route('/viewrawmaterial')
def viewrawmaterial():
    db=Db()
    qry=db.select("select * from rawmaterial")
    return render_template("Admin/admin_viewRawMaterial.html",data=qry)

@app.route('/updateraw/<id>')
def updateraw(id):
    db=Db()
    qry=db.selectOne("select * from rawmaterial where raw_id='"+id+"'")
    return render_template("Admin/update_rawmaterial.html",data=qry)

@app.route('/updateraw1/<id>',methods=['post'])
def updateraw1(id):
    db=Db()
    name=request.form['raw_name']
    quantity=request.form['raw_quantity']
    desc=request.form['description']
    qry=db.update("update rawmaterial set raw_name='"+name+"',quantity='"+quantity+"',description='"+desc+"' where raw_id='"+id+"'")
    return viewrawmaterial()


@app.route('/deleteraw/<a>')
def deleteraw(a):
    db=Db()
    qry=db.delete("delete from rawmaterial where raw_id='"+a+"'")
    return'<script>alert("Removed..."); window.location="/viewrawmaterial"</script>'



@app.route('/viewreqrawmaterials')
def viewreqrawmaterials():
    return render_template("Admin/admin_viewrequiredRaw.html")

@app.route('/viewschedule')
def viewschedule():
    return render_template("Admin/admin_viewSchedule.html")


@app.route('/viewservice')
def viewservice():
    db=Db()
    qry=db.select("select * from service where status='1'")
    return render_template("Admin/admin_viewService.html",data=qry)

@app.route('/delete/<a>')
def delete(a):
    db=Db()
    rq=db.update("update service set status='0' WHERE service_id='"+a+"'")
    return '<script>alert("Removed....");window.location="/viewservice"</script>'

@app.route('/enableservice')
def enableservice():
    db=Db()
    qry=db.select("select * from service where status='0'")
    return render_template("Admin/disabled_services.html",dataa=qry)


@app.route('/enable/<a>')
def enable(a):
    db=Db()
    rq=db.update("update service set status='1' WHERE service_id='"+a+"'")
    return '<script>alert("Successfully added.....");window.location="/enableservice"</script>'


@app.route('/viewworker')
def viewworker():
    db=Db()
    qry=db.select("select * from worker")
    return render_template("Admin/admin_viewWorker.html",data=qry)

@app.route('/editworker/<id>')
def editworker(id):
    db=Db()
    qry=db.selectOne("select * from worker where workerid='"+id+"'")
    return render_template("Admin/edit_worker.html",data=qry)


@app.route('/editworker1/<id>',methods=['post'])
def editworker1(id):
    db = Db()
    name = request.form['txt_name']
    dob = request.form['txt_dob']
    mobile = request.form['txt_mobile']
    adhar = request.form['txt_adhar']
    email = request.form['txt_email']
    house = request.form['txt_house']
    place = request.form['txt_place']
    post = request.form['txt_post']
    pin = request.form['txt_pin']
    photo = request.files['photo']
    wages=request.form['wages']
    if request.files is not None:
        if photo.filename !="":
            date = datetime.datetime.now().strftime("%y%m%d-%H%M%S")
            photo.save(path + date + ".jpg")
            pth = "/static/photos/" + date + ".jpg"
            qry=db.update("update worker set worker_name='"+name+"',dob='"+dob+"',phone_no='"+mobile+"',adhar_no='"+adhar+"',worker_email='"+email+"',house_name='"+house+"',place='"+place+"',post='"+post+"',pin='"+pin+"',photo='"+str(pth)+"',wages='"+wages+"' where workerid='"+id+"'")
            return viewworker()
        else:
            qry=db.update("update worker set worker_name='"+name+"',dob='"+dob+"',phone_no='"+mobile+"',adhar_no='"+adhar+"',worker_email='"+email+"',house_name='"+house+"',place='"+place+"',post='"+post+"',pin='"+pin+"',wages='"+wages+"' where workerid='"+id+"'")
            return viewworker()
    else:
        qry = db.update("update worker set worker_name='" + name + "',dob='" + dob + "',phone_no='" + mobile + "',adhar_no='" + adhar + "',worker_email='" + email + "',house_name='" + house + "',place='" + place + "',post='" + post + "',pin='" + pin + "',wages='"+wages+"' where workerid='" + id + "'")
        return viewworker()


@app.route('/deleteworker/<a>')
def deleteworker(a):
    db=Db()
    qry=db.delete("delete from worker where workerid='"+a+"'")
    return'<script>alert("Removeed..."); window.location="/viewworker"</script>'


@app.route('/viewworkhistory')
def viewworkhistory():
    return render_template("Admin/admin_viewWorkHistory.html")


@app.route('/view_rating_review')
def view_rating_review():
    db=Db()
    q = db.select("select * from review,client WHERE review.client_id=client.client_id")
    return render_template("Admin/view_rating_review.html",data=q)












if __name__ == '__main__':
    app.run(debug=True,port=4000)
