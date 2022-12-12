let indexTeacher = 0;
let indexTeacherAppUser = 0;
let indexTeacherAppUserRole = 0;


function getTeacher() {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/teachers`,
        success: function (data) {
            let content = '<tr>\n' +
                '<th>Name</th>\n' +
                '<th>Birth</th>\n' +
                '<th>Gender</th>\n' +
                '<th>Phone Number</th>\n' +
                '<th>Email</th>\n' +
                '<th>Identify</th>\n' +
                '<th>Picture</th>\n' +
                '<th colspan="2">Action</th>\n' +
                '</tr>';
            for (let i = 0; i < data.length; i++) {
                content += displayTeacher(data[i]);
            }
            document.getElementById("teacherList").innerHTML = content;
            document.getElementById("formTeacher").hidden = true;
            document.getElementById("formEditTeacher").hidden = true;
        }
    });
}

function displayTeacher(teacher) {
    return `<tr><td>${teacher.appUser.fullName}</td><td>${teacher.appUser.birth}</td>
            <td>${teacher.gender}</td><td>${teacher.appUser.phoneNumber}</td>
            <td>${teacher.appUser.email}</td><td>${teacher.appUser.identify}</td>
            <td><img src="${teacher.image}" alt="loi"></td>
            <td><button class="btn btn-danger" onclick="deleteTeacher(${teacher.id})">Delete</button></td>
            <td><button class="btn btn-warning" onclick="editTeacherGet(${teacher.id})">Edit</button></td></tr>`;
}

function displayFormCreateTeacher() {
    document.getElementById("formTeacher").reset()
    document.getElementById("formTeacher").hidden = false;
    document.getElementById("formTeacher-button").onclick = function () {
        addNewTeacher();
    }
    getAccountTeacher();
    getClasses()
}

function getAccountTeacher() {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/users`,
        success: function (data) {
            let content = '<select id="accountTeacher">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayAccountTeacher(data[i]);
            }
            content += '</select>'
            document.getElementById('div-teacher-appUser').innerHTML = content;
        }
    });
}

function displayAccountTeacher(appUser) {
    return `<option id="${appUser.id}" value="${appUser.id}">${appUser.fullName}</option>`
}


function getClasses() {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/admin/etc`,
        //xử lý khi thành công
        success: function (data) {
            let content = '<select id="classesTeacherCreate">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayClassesTeacher(data[i]);
            }
            content += '</select>'
            document.getElementById('div-classesCreate').innerHTML = content;
        }
    });
}

function displayClassesTeacher(classes) {
    return `<option id="${classes.id}" value="${classes.id}">${classes.name}</option>`;
}

function addNewTeacher() {
    let name = $('#nameTeacher').val();
    let gender = $('#genderTeacher').val();
    let appUser = $('#accountTeacher').val();
    let classes = $('#classesTeacherCreate').val();
    let newTeacher = {
        name: name,
        gender: gender,
        classes: {
            id: classes,
        },
        appUser: {
            id: appUser
        }
    };
    let data = new FormData();
    data.append("file", $('#imageTeacher')[0].files[0])
    data.append("json", new Blob([JSON.stringify(newTeacher)], {
        type: "application/json"
    }))
    $.ajax({
        type: "POST",
        data: data,
        processData: false,
        contentType: false,
        url: "http://localhost:8080/admin/teachers",
        success: function () {
            getTeacherByPage(0);
        }
    });
    event.preventDefault();
}

function deleteTeacher(id) {
    $.ajax({
        type: "DELETE",
        url: `http://localhost:8080/admin/teachers/${id}`,
        success: function () {
            getTeacherByPage(0);
        }
    });
}

function editTeacherGet(id) {
    getClassesEdit()
    getRoleTeacherEdit();

    editTeacher(id)
    document.getElementById('formTeacher').hidden = true;
    document.getElementById('formEditTeacher').hidden = false;
    document.getElementById("form-button-teacher-edit").onclick = function () {
        editTeacher1(indexTeacher);
    }


}

function editTeacher(id) {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/teachers/${id}`,
        success: function (data) {
            $('#nameTeacherEdit').val(data.name);
            $('#genderTeacherEdit').val(data.gender);
            indexTeacher = data.id;
            indexTeacherAppUser = data.appUser.id;
            indexTeacherAppUserRole = data.appUser.role.id;
            editTeacherAppUser(indexTeacherAppUser);
        }
    });
}

function editTeacherAppUser(id) {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/users/${id}`,
        success: function (appUser) {
            $('#userTeacherFullName').val(appUser.fullName);
            $('#userTeacherEmail').val(appUser.email);
            $('#accountTeacherUsername').val(appUser.username);
            $('#teacherPassword').val(appUser.password);
            $('#teacherRePassword').val(appUser.rePassword);
            $('#teacherPhoneNumber').val(appUser.phoneNumber);
            $('#teacherBirth').val(appUser.birth);
            $('#teacherAddress').val(appUser.address);
            $('#teacherIdentify').val(appUser.identify);
        }
    });
}

function editTeacher1(id) {
    let name = $('#nameTeacherEdit').val();
    let gender = $('#genderTeacherEdit').val();
    let classes = $('#classesTeacherEdit').val();
    let newTeacher = {
        name: name,
        gender: gender,
        appUser: {id: indexTeacherAppUser},
        classes: {id: classes}
    };
    let data = new FormData;
    data.append("file", $('#imageTeacherEdit')[0].files[0]);
    data.append("json", new Blob([JSON.stringify(newTeacher)], {
        type: "application/json"
    }))
    $.ajax({
        type: "PUT",
        data: data,
        processData: false,
        contentType: false,
        url: `http://localhost:8080/admin/teachers/${id}`,
        success: function () {
            editTeacherAppUser1(indexTeacherAppUser);
        }
    });
    event.preventDefault();
}

function editTeacherAppUser1(id) {
    let fullName = $('#userTeacherFullName').val();
    let email = $('#userTeacherEmail').val();
    let username = $('#accountTeacherUsername').val();
    let password = $('#teacherPassword').val();
    let rePassword = $('#teacherRePassword').val();
    let phoneNumber = $('#teacherPhoneNumber').val();
    let birth = $('#teacherBirth').val();
    let address = $('#teacherAddress').val();
    let identify = $('#teacherIdentify').val();

    let newUserEdit = {
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
            id: indexTeacherAppUserRole,
        },
    };
    let data = new FormData;
    data.append("json", new Blob([JSON.stringify(newUserEdit)], {
        type: "application/json"
    }))
    $.ajax({
        type: "PUT",
        data: data,
        processData: false,
        contentType: false,
        url: `http://localhost:8080/admin/users/${id}`,
        success: function () {
          getTeacherByPage(0);
        }
    });
    event.preventDefault();
}

function getTeacherByPage(page) {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/admin/teachers/page?page=${page}`,
        //xử lý khi thành công
        success: function (data) {
            let array = data.content
            let content = '<tr>\n' +
                '<th>Name</th>\n' +
                '<th>Birth</th>\n' +
                '<th>Gender</th>\n' +
                '<th>Phone Number</th>\n' +
                '<th>Email</th>\n' +
                '<th>Identify</th>\n' +
                '<th>Picture</th>\n' +
                '<th colspan="2">Action</th>\n' +
                '</tr>';
            for (let i = 0; i < array.length; i++) {
                content += displayTeacher(array[i]);
            }

            document.getElementById("teacherList").innerHTML = content;
            document.getElementById("displayPageTeacher").innerHTML = displayPageTeacher(data)
            document.getElementById("formEditTeacher").hidden = true;
            if (data.pageable.pageNumber === 0) {
                document.getElementById("backup").hidden = true
            }
            if (data.pageable.pageNumber + 1 === data.totalPages) {
                document.getElementById("next").hidden = true
            }
        }
    });
}

function displayPageTeacher(data) {
    return `<button class="btn btn-primary" id="backup" onclick="isPreviousTeacher(${data.pageable.pageNumber})">Previous</button>
    <span>${data.pageable.pageNumber + 1} | ${data.totalPages}</span>
    <button class="btn btn-primary" id="next" onclick="isNextTeacher(${data.pageable.pageNumber})">Next</button>`
}

function isPreviousTeacher(pageNumber) {
    getTeacherByPage(pageNumber - 1)
}

function isNextTeacher(pageNumber) {
    getTeacherByPage(pageNumber + 1)
}

function searchTeacher() {
    let searchTeacher = document.getElementById("searchTeacher").value;
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/teachers/search?search=${searchTeacher}`,
        success: function (data) {
            let content = '<tr>\n' +
                '<th>Name</th>\n' +
                '<th>Birth</th>\n' +
                '<th>Gender</th>\n' +
                '<th>Phone Number</th>\n' +
                '<th>Email</th>\n' +
                '<th>Identify</th>\n' +
                '<th>Picture</th>\n' +
                '<th colspan="2">Action</th>\n' +
                '</tr>';
            for (let i = 0; i < data.length; i++) {
                content += displayTeacher(data[i]);
            }
            document.getElementById('teacherList').innerHTML = content;
            document.getElementById("searchTeacher").reset()
            document.getElementById("formEditTeacher").hidden = true;
            if (data.pageable.pageNumber === 0) {
                document.getElementById("backup").hidden = true
            }
            if (data.pageable.pageNumber + 1 === data.totalPages) {
                document.getElementById("next").hidden = true
            }
        }
    });
    event.preventDefault();
}


function getClassesEdit() {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/admin/etc`,
        //xử lý khi thành công
        success: function (data) {
            let content = '<select id="classesTeacherEdit">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayClassesEdit(data[i]);
            }
            content += '</select>'
            document.getElementById('div-classes').innerHTML = content;
        }
    });
}

function displayClassesEdit(classes) {
    return `<option id="${classes.id}" value="${classes.id}">${classes.name}</option>`;
}

function getRoleTeacherEdit() {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/roles`,
        success: function (data) {
            let content = '<select id="roleTeacherEdit">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayRoleTeacherEdit(data[i]);
            }
            content += '</select>'
            document.getElementById('div-role-teacher-edit').innerHTML = content;
        }
    });
}

function displayRoleTeacherEdit(role) {
    return `<option id="${role.id}" value="${role.id}">${role.name}</option>`;
}

function displayManagerTeacher() {
    document.getElementById("manager-teacher").hidden = false;
    document.getElementById("manager-user").hidden = true;
    document.getElementById("manager-student").hidden = true;
    document.getElementById("manager-officer").hidden = true;
    document.getElementById("manager-course").hidden = true;
    getTeacherByPage(0)
}



