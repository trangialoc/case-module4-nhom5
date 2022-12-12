let indexStudent = 0;
let indexStudentAccount = 0;

function getStudent() {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/students`,
        success: function (data) {
            let content = '<tr>\n' +
                '<th>Full Name</th>\n' +
                '<th>Birth</th>\n' +
                '<th>Gender</th>\n' +
                '<th>Phone Number</th>\n' +
                '<th>Email</th>\n' +
                '<th>Username</th>\n' +
                '<th>Identify</th>\n' +
                '<th>Class</th>\n' +
                '<th>Course</th>\n' +
                '<th>Status</th>\n' +
                '<th>Picture</th>\n' +
                '<th colspan="2">Action</th>\n' +
                '</tr>';
            for (let i = 0; i < data.length; i++) {
                content += displayStudent(data[i]);
            }
            document.getElementById("studentList").innerHTML = content;
            document.getElementById("formStudent").hidden = true;
            document.getElementById("formEditStudent").hidden = true;
        }
    });
}
function displayStudent(student) {
    return `<tr><td>${student.appUser.fullName}</td><td>${student.appUser.birth}</td>
            <td>${student.gender}</td><td>${student.appUser.phoneNumber}</td>
            <td>${student.appUser.email}</td><td>${student.appUser.username}</td>
            <td>${student.appUser.identify}</td> <td>${student.classes.name}</td> <td>${student.course.name}</td>
            <td>${student.statusStudent.status}</td>
            <td><img src="${student.image}" alt="loi"></td>
            <td><button class="btn btn-danger" onclick="deleteStudent(${student.id})">Delete</button></td>
            <td><button class="btn btn-warning" onclick="editStudentGet(${student.id})">Edit</button></td></tr>`;
}

function displayFormCreateStudent() {
    document.getElementById("formStudent").reset()
    document.getElementById("formStudent").hidden = false;
    document.getElementById("form-button-student").onclick = function () {
        addNewStudent();
    }
    getStatusStudent();
    getClassesStudent();
    getAccountStudent();
    getCourses();
}
function getStatusStudent(){
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/statusStudents`,
        success: function (data) {
            let content = '<select id="statusStudent">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayStatusStudent(data[i]);
            }
            content += '</select>'
            document.getElementById('div-status-student').innerHTML = content;
        }
    });
}

function displayStatusStudent(status){
    return `<option id="${status.id}" value="${status.id}">${status.status}</option>`
}

function getAccountStudent(){
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/users`,
        success: function (data) {
            let content = '<select id="accountStudent">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayAccountStudent(data[i]);
            }
            content += '</select>'
            document.getElementById('div-account-student').innerHTML = content;
        }
    });
}

function displayAccountStudent(account){
    return `<option id="${account.id}" value="${account.id}">${account.fullName}</option>`
}

function getClassesStudent(){
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/etc`,
        //xử lý khi thành công
        success: function (data) {
            let content = '<select id="classesStudent">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayClassesStudent(data[i]);
            }
            content += '</select>'
            document.getElementById('div-classes-student').innerHTML = content;
        }
    });
}

function displayClassesStudent(classes) {
    return `<option id="${classes.id}" value="${classes.id}">${classes.name}</option>`;
}

function getCourses(){
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/courses`,
        success: function (data) {
            let content = '<select id="courseStudent">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayCourse(data[i]);
            }
            content += '</select>'
            document.getElementById('div-course').innerHTML = content;
        }
    });
}

function displayCourse(course){
    return `<option id="${course.id}" value="${course.id}">${course.name}</option>`
}

function addNewStudent() {
    let data = new FormData();
    let name = $('#nameStudent').val();
    let gender = $('#genderStudent').val();
    let account = $('#accountStudent').val();
    let classes = $('#classesStudent').val();
    let course = $('#courseStudent').val();
    let status = $('#statusStudent').val();
    let newTeacher = {
        name: name,
        gender: gender,

        appUser: {
            id: account,
        },
        classes:{
            id:classes,
        },
        course:{
            id:course
        },
        statusStudent:{
            id: status
        }
    };
    data.append("file", $('#imageStudent')[0].files[0])
    data.append("json", new Blob([JSON.stringify(newTeacher)],{
        type: "application/json"
    }))
    $.ajax({
        type: "POST",
        data: data,
        processData: false,
        contentType: false,
        url: "http://localhost:8080/admin/students",
        success: function () {
            getStudentByPage(0);
        }

    });
    event.preventDefault();
}

function deleteStudent(id) {
    $.ajax({
        type: "DELETE",
        url: `http://localhost:8080/admin/students/${id}`,
        success: function () {
            getStudentByPage(0)
        }
    });
}

function editStudentGet(id){
    getStatusStudentEdit();
    getClassesStudentEdit();
    getCoursesEdit();
    getRoleStudentEdit();
    getAccountStudentEdit()
    editStudent(id);
    document.getElementById("formEditStudent").hidden = false;
    document.getElementById("form-button-student-edit").onclick = function () {
        editStudent1(indexStudent);
    }
}
function editStudent(id) {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/admin/students/${id}`,
        //xử lý khi thành công
        success: function (data) {
            $('#nameStudentEdit').val(data.name);
            $('#genderStudentEdit').val(data.gender);
            indexStudent = data.id;
            indexStudentAccount = data.appUser.id;
            editStudentAccount(indexStudentAccount);
        }
    });
}
function editStudentAccount(id){
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/users/${id}`,
        success: function (appUser) {
            $('#userStudentFullName').val(appUser.fullName);
            $('#userStudentEmail').val(appUser.email);
            $('#accountStudentName').val(appUser.username);
            $('#studentPassword').val(appUser.password);
            $('#studentRePassword').val(appUser.rePassword);
            $('#studentPhoneNumber').val(appUser.phoneNumber);
            $('#studentBirth').val(appUser.birth);
            $('#studentAddress').val(appUser.address);
            $('#studentIdentify').val(appUser.identify);
        }
    });
}

function editStudent1(id) {
    let name = $('#nameStudentEdit').val();
    let gender = $('#genderStudentEdit').val();
    let course = $('#courseStudentEdit').val();
    let classes = $('#classesStudentEdit').val();
    let account = $('#accountStudentEdit').val();
    let status = $('#statusStudentEdit').val();
    let newTeacher = {
        name: name,
        gender: gender,
        course: {id: course} ,
        classes: {id:classes},
        appUser: {id:account},
        statusStudent:{id:status}
    };
    let data = new FormData;
    data.append("file", $('#imageStudentEdit')[0].files[0]);
    data.append("json", new Blob([JSON.stringify(newTeacher)],{
        type: "application/json"
    }))
    $.ajax({
        type: "PUT",
        data: data,
        processData: false,
        contentType: false,
        url: `http://localhost:8080/admin/students/${id}`,
        success: function () {
            editStudentAccount1(indexStudentAccount);
        }
    });
    event.preventDefault();
}
function editStudentAccount1(id){
    let fullName = $('#userStudentFullName').val();
    let email = $('#userStudentEmail').val();
    let username = $('#accountStudentName').val();
    let password = $('#studentPassword').val();
    let rePassword = $('#studentRePassword').val();
    let phoneNumber = $('#studentPhoneNumber').val();
    let birth = $('#studentBirth').val();
    let address = $('#studentAddress').val();
    let identify = $('#studentIdentify').val();
    let role = $('#roleStudentEdit').val();

    let newUser = {
        fullName: fullName,
        email: email,
        username: username,
        password: password,
        rePassword: rePassword,
        phoneNumber: phoneNumber,
        birth: birth,
        address: address,
        identify: identify,
        role: {
            id: role,
        },
    };
    let data = new FormData;
    data.append("json", new Blob([JSON.stringify(newUser)],{
        type: "application/json"
    }))
    $.ajax({
        type: "PUT",
        data: data,
        processData: false,
        contentType: false,
        url: `http://localhost:8080/admin/users/${id}`,
        success: function () {
          getStudentByPage(0);
        }
    });
    event.preventDefault();
}
function displayStudentHeard(){
   return` <tr>
    <th>Full Name</th>
    <th>Birth</th>
    <th>Gender</th>
    <th>Phone Number</th>
    <th>Email</th>
    <th>Username</th>
    <th>Identify</th>
    <th>Class</th>
    <th>Course</th>
    <th>Status</th>
    <th>Picture</th>
    <th colspan="2">Action</th>
    </tr>`;
}


function getStudentByPage(page) {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/students/page?page=${page}`,
        success: function (data) {
            let  array = data.content
            let  content = displayStudentHeard();
            for (let i = 0; i < array.length; i++) {
                content += displayStudent(array[i]);
            }
            document.getElementById("studentList").innerHTML = content;
            document.getElementById("displayPageStudent").innerHTML = displayPageStudent(data)
            document.getElementById("formStudent").hidden = true;
            document.getElementById("formEditStudent").hidden = true;
            if (data.pageable.pageNumber === 0) {
                document.getElementById("backupStudent").hidden = true
            }
            if (data.pageable.pageNumber + 1 === data.totalPages) {
                document.getElementById("nextStudent").hidden = true
            }
        }
    });
}

function displayPageStudent(data){
    return `<button class="btn btn-primary" id="backupStudent" onclick="isPreviousStudent(${data.pageable.pageNumber})">Previous</button>
    <span>${data.pageable.pageNumber+1} | ${data.totalPages}</span>
    <button class="btn btn-primary" id="nextStudent" onclick="isNextStudent(${data.pageable.pageNumber})">Next</button>`
}

function isPreviousStudent(pageNumber) {
    getStudentByPage(pageNumber-1)
}

function isNextStudent(pageNumber) {
    getStudentByPage(pageNumber+1)
}

function searchStudent() {
    let searchStudent = document.getElementById("searchStudent").value;
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/students/search?search=${searchStudent}`,
        success: function (data) {
            let content = '<tr>\n' +
                '<th>Full Name</th>\n' +
                '<th>Birth</th>\n' +
                '<th>Gender</th>\n' +
                '<th>Phone Number</th>\n' +
                '<th>Email</th>\n' +
                '<th>Username</th>\n' +
                '<th>Identify</th>\n' +
                '<th>Class</th>\n' +
                '<th>Course</th>\n' +
                '<th>Picture</th>\n' +
                '<th colspan="2">Action</th>\n' +
                '</tr>';
            for (let i = 0; i < data.length; i++) {
                content += displayStudent(data[i]);
            }
            document.getElementById('studentList').innerHTML = content;
        }
    });
    event.preventDefault();
}

function getStatusStudentEdit(){
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/admin/statusStudents`,
        //xử lý khi thành công
        success: function (data) {
            let content = '<select id="statusStudentEdit">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayStatusStudentEdit(data[i]);
            }
            content += '</select>'
            document.getElementById('div-statusStudent-edit').innerHTML = content;
        }
    });
}

function displayStatusStudentEdit(status){
    return `<option id="${status.id}" value="${status.id}">${status.status}</option>`
}

function getClassesStudentEdit(){
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/etc`,
        success: function (data) {
            let content = '<select id="classesStudentEdit">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayClassesStudentEdit(data[i]);
            }
            content += '</select>'
            document.getElementById('div-classes-student-edit').innerHTML = content;
        }
    });
}

function displayClassesStudentEdit(classes) {
    return `<option id="${classes.id}" value="${classes.id}">${classes.name}</option>`;
}

function getCoursesEdit(){
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/admin/courses`,
        //xử lý khi thành công
        success: function (data) {
            let content = '<select id="courseStudentEdit">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayCourseEdit(data[i]);
            }
            content += '</select>'
            document.getElementById('div-course-edit').innerHTML = content;
        }
    });
}

function displayCourseEdit(course){
    return `<option id="${course.id}" value="${course.id}">${course.name}</option>`
}

function getRoleStudentEdit() {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/admin/roles`,
        //xử lý khi thành công
        success: function (data) {
            let content = '<select id="roleStudentEdit">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayRoleStudentEdit(data[i]);
            }
            content += '</select>'
            document.getElementById('div-role-edit').innerHTML = content;
        }
    });
}

function displayRoleStudentEdit(role) {
    return `<option id="${role.id}" value="${role.id}">${role.name}</option>`;
}
function getAccountStudentEdit() {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/admin/users`,
        //xử lý khi thành công
        success: function (data) {
            let content = '<select id="accountStudentEdit">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayAccountStudentEdit(data[i]);
            }
            content += '</select>'
            document.getElementById('div-account-student-edit').innerHTML = content;
        }
    });
}

function displayAccountStudentEdit(account) {
    return `<option id="${account.id}" value="${account.id}">${account.fullName}</option>`;
}



function displayManagerStudent(){
    document.getElementById("manager-student").hidden=false;
    document.getElementById("manager-user").hidden=true;
    document.getElementById("manager-teacher").hidden=true;
    document.getElementById("manager-officer").hidden=true;
    getStudentByPage(0);
}




