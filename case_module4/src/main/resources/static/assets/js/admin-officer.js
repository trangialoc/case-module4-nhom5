let indexOfficer = 0;
let indexOfficerAccount = 0


function getOfficer() {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/admin/officers`,
        //xử lý khi thành công
        success: function (data) {
            // hien thi danh sach o day
            let content = '<tr>\n' +
                '<th>Full Name</th>\n' +
                '<th>Birth</th>\n' +
                '<th>Gender</th>\n' +
                '<th>Phone Number</th>\n' +
                '<th>Email</th>\n' +
                '<th>Identify</th>\n' +
                '<th>Picture</th>\n' +
                '<th colspan="2">Action</th>\n' +
                '</tr>';
            for (let i = 0; i < data.length; i++) {
                content += displayOfficer(data[i]);
            }
            document.getElementById("officerList").innerHTML = content;
            document.getElementById("formOfficer").hidden = true;
            document.getElementById("formEditOfficer").hidden = true;
        }
    });
}

function displayOfficer(officer) {
    return `<tr>
            <td>${officer.appUser.fullName}</td>
            <td>${officer.appUser.birth}</td>
            <td>${officer.gender}</td>
            <td>${officer.appUser.phoneNumber}</td>
            <td>${officer.appUser.email}</td>
            <td>${officer.appUser.identify}</td>
            <td><img src="${officer.image}" alt="loi"></td>
            <td><button class="btn btn-danger" onclick="deleteOfficer(${officer.id})">Delete</button></td>
            <td><button class="btn btn-warning" onclick="editOfficerGet(${officer.id})">Edit</button></td></tr>`;
}

function displayFormCreateOfficer() {
    document.getElementById("formOfficer").reset()
    document.getElementById("formOfficer").hidden = false;
    document.getElementById("formOfficer-button").onclick = function () {
        addNewOfficer();
    }
    getAccountOfficer();
}

function getAccountOfficer() {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/users`,
        success: function (data) {
            let content = '<select id="accountOfficer">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayAccountOfficer(data[i]);
            }
            content += '</select>'
            document.getElementById('div-account-officer').innerHTML = content;
        }
    });
}

function displayAccountOfficer(account) {
    return `<option id="${account.id}" value="${account.id}">${account.fullName}</option>`
}


function addNewOfficer() {
    let data = new FormData();
    let name = $('#nameOfficer').val();
    let gender = $('#genderOfficer').val();
    let account = $('#accountOfficer').val();
    let newOfficer = {
        name: name,
        gender: gender,

        appUser: {
            id: account,
        },
    };
    data.append("file", $('#imageOfficer')[0].files[0])
    data.append("json", new Blob([JSON.stringify(newOfficer)], {
        type: "application/json"
    }))
    $.ajax({
        type: "POST",
        data: data,
        processData: false,
        contentType: false,
        url: "http://localhost:8080/admin/officers",
        success: function () {
            getOfficerByPage(0);
        }

    });
    event.preventDefault();
}

function editOfficerGet(id) {
    getRoleOfficerEdit();
    getAccountOfficerEdit();
    editOfficer(id);

    document.getElementById("formEditOfficer").hidden = false;
    document.getElementById("form-button-officer-edit").onclick = function () {
        editOfficer1(indexOfficer);
    }
}

function editOfficer(id) {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/officers/${id}`,
        success: function (data) {
            $('#nameOfficerEdit').val(data.name);
            $('#genderOfficerEdit').val(data.gender);
            indexOfficer = data.id;
            indexOfficerAccount = data.appUser.id;
            editOfficerAccount(indexOfficerAccount);
        }
    });
}

function editOfficerAccount(id) {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/users/${id}`,
        success: function (appUser) {
            $('#userOfficerFullName').val(appUser.fullName);
            $('#userOfficerEmail').val(appUser.email);
            $('#accountOfficerUsername').val(appUser.username);
            $('#officerPassword').val(appUser.password);
            $('#officerRePassword').val(appUser.rePassword);
            $('#officerPhoneNumber').val(appUser.phoneNumber);
            $('#officerBirth').val(appUser.birth);
            $('#officerAddress').val(appUser.address);
            $('#officerIdentify').val(appUser.identify);
        }
    });
}


function editOfficer1(id) {
    let name = $('#nameOfficerEdit').val();
    let gender = $('#genderOfficerEdit').val();
    let appUser = $('#accountOfficerEdit').val();
    let newOfficer = {
        name: name,
        gender: gender,
        appUser: {id: appUser},
    };
    let data = new FormData;
    data.append("file", $('#imageOfficerEdit')[0].files[0]);
    data.append("json", new Blob([JSON.stringify(newOfficer)], {
        type: "application/json"
    }))
    $.ajax({
        type: "PUT",
        data: data,
        processData: false,
        contentType: false,
        url: `http://localhost:8080/admin/officers/${id}`,
        success: function () {
            editOfficerAccount1(indexOfficerAccount);
        }
    });
    event.preventDefault();
}

function editOfficerAccount1(id) {
    let fullName = $('#userOfficerFullName').val();
    let email = $('#userOfficerEmail').val();
    let username = $('#accountOfficerUsername').val();
    let password = $('#officerPassword').val();
    let rePassword = $('#officerRePassword').val();
    let phoneNumber = $('#officerPhoneNumber').val();
    let birth = $('#officerBirth').val();
    let address = $('#officerAddress').val();
    let identify = $('#officerIdentify').val();
    let role = $('#roleOfficerEdit').val();

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
            id: role,
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
            getOfficerByPage(0);
        }
    });
    //chặn sự kiện mặc định của thẻ
    event.preventDefault();
}

function getOfficerByPage(page) {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/admin/officers/page?page=${page}`,
        //xử lý khi thành công
        success: function (data) {
            let array = data.content
            let content = '<tr>\n' +
                '<th>Full Name</th>\n' +
                '<th>Birth</th>\n' +
                '<th>Gender</th>\n' +
                '<th>Phone Number</th>\n' +
                '<th>Email</th>\n' +
                '<th>Identify</th>\n' +
                '<th>Picture</th>\n' +
                '<th colspan="2">Action</th>\n' +
                '</tr>';
            for (let i = 0; i < array.length; i++) {
                content += displayOfficer(array[i]);
            }

            document.getElementById("officerList").innerHTML = content;
            document.getElementById("displayPageOfficer").innerHTML = displayPageOfficer(data)
            document.getElementById("formEditOfficer").hidden = true;
            if (data.pageable.pageNumber === 0) {
                document.getElementById("backup").hidden = true
            }
            if (data.pageable.pageNumber + 1 === data.totalPages) {
                document.getElementById("next").hidden = true
            }
        }
    });
}

function displayPageOfficer(data) {
    return `<button class="btn btn-primary" id="backup" onclick="isPreviousOfficer(${data.pageable.pageNumber})">Previous</button>
    <span>${data.pageable.pageNumber + 1} | ${data.totalPages}</span>
    <button class="btn btn-primary" id="next" onclick="isNextOfficer(${data.pageable.pageNumber})">Next</button>`
}

function isPreviousOfficer(pageNumber) {
    getOfficerByPage(pageNumber - 1)
}

function isNextOfficer(pageNumber) {
    getOfficerByPage(pageNumber + 1)
}


function searchOfficer() {
    let searchOfficer = document.getElementById("searchOfficer").value;
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/officers/search?search=${searchOfficer}`,
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
                content += displayOfficer(data[i]);
            }
            document.getElementById('officerList').innerHTML = content;
            document.getElementById("searchOfficer").reset()
            document.getElementById("formEditOfficer").hidden = true;
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
function getRoleOfficerEdit() {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/roles`,
        success: function (data) {
            let content = '<select id="roleOfficerEdit">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayRoleOfficerEdit(data[i]);
            }
            content += '</select>'
            document.getElementById('div-role-officer-edit').innerHTML = content;
        }
    });
}

function displayRoleOfficerEdit(role) {
    return `<option id="${role.id}" value="${role.id}">${role.name}</option>`;
}

function getAccountOfficerEdit() {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/admin/users`,
        //xử lý khi thành công
        success: function (data) {
            let content = '<select id="accountOfficerEdit">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayAccountOfficerEdit(data[i]);
            }
            content += '</select>'
            document.getElementById('div-account-officer-edit').innerHTML = content;
        }
    });
}

function displayAccountOfficerEdit(account) {
    return `<option id="${account.id}" value="${account.id}">${account.fullName}</option>`;
}

function displayManagerOfficer() {
    document.getElementById("manager-teacher").hidden = true;
    document.getElementById("manager-user").hidden = true;
    document.getElementById("manager-student").hidden = true;
    document.getElementById("manager-officer").hidden = false;
    document.getElementById("manager-course").hidden = true;
    getOfficerByPage();
}
