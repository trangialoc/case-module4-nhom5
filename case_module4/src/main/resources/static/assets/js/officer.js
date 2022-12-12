let classes = "";
let indexStudent = 0;

function getPoint(idStudent) {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/officers/getPoint/${idStudent}`,
        success: function (data) {
            let content1 = '';
            let content = '<tr>\n' +
                '<th>Name</th>\n' +
                '<th>Practice Point</th>\n' +
                '<th>Theoretical Point</th>\n' +
                '</tr>';
            for (let i = 0; i < data.length; i++) {
                content += displayPointOfficer(data[i]);
            }
           content1 +=`<tr><td>AvgPoint</td>
            <td>${avgPractisePoint(data)}</td>
            <td>${theoreticalPoint(data)}</td></tr>`;

            document.getElementById("pointListOfficer").innerHTML = content;
            document.getElementById("avgPointTable").innerHTML = content1;
            document.getElementById("div-point-officer").hidden = false;
            document.getElementById("formPoint").hidden = true;

        }
    });
}
function avgPractisePoint(points){
    let avgPractisePoint = 0;

    for (let i = 0; i < points.length; i++) {
        avgPractisePoint += points[i].practicePoint
    }
    return avgPractisePoint/points.length;
}

function theoreticalPoint(points){
    let avgTheoreticalPoint = 0;
    for (let i = 0; i < points.length; i++) {
        avgTheoreticalPoint += points[i].theoreticalPoint
    }
    return avgTheoreticalPoint/points.length;
}

function displayPointOfficer(point) {
    return `<tr><td>${point.name}</td>
            <td>${point.practicePoint}</td>
            <td>${point.theoreticalPoint}</td></tr>`;
}

function displayClasses() {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/officers/showClass`,
        success: function (data) {
            let content = '<select id="showClasses">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayClassAddPoint(data[i]);
            }
            content += '</select>'
            document.getElementById('div-displayClasses').innerHTML = content;
            document.getElementById('addPoint').hidden = false;
            document.getElementById('chooseClassAddPoint').hidden = false;
            document.getElementById('div-point-officer').hidden = true;
            document.getElementById('div-pointEdit-officer').hidden = true;

            document.getElementById("form-button-addPoint").onclick = function () {
                chooseClasses();
            }

        }
    });
}

function displayClassAddPoint(classes) {
    return `<option id="${classes.id}" value="${classes.name}">${classes.name}</option>`
}


function chooseClasses() {
    classes = $('#showClasses').val();
    findStudentByClass(classes)

}


function findStudentByClass(classes) {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/officers/showStudent/${classes}`,
        //xử lý khi thành công
        success: function (data) {
            let content = '<tr>\n' +
                '<th>Full Name</th>\n' +
                '<th>Gender</th>\n' +
                '<th>Phone Number</th>\n' +
                '<th>Email</th>\n' +
                '<th>Username</th>\n' +
                '<th>Class</th>\n' +
                '<th>Course</th>\n' +
                '<th>Status</th>\n' +
                '<th>Picture</th>\n' +
                '<th colspan="2">Action</th>\n' +
                '</tr>';
            for (let i = 0; i < data.length; i++) {
                content += displayStudentByClass(data[i]);
            }

            document.getElementById('studentListAddPoint').innerHTML = content;
            document.getElementById('showTableStudent').hidden = false;
        }
    });

}

function displayStudentByClass(student) {
    return `<tr><td>${student.appUser.fullName}</td>
            <td>${student.gender}</td><td>${student.appUser.phoneNumber}</td>
            <td>${student.appUser.email}</td><td>${student.appUser.username}</td>
            <td>${student.classes.name}</td> <td>${student.course.name}</td>
            <td>${student.statusStudent.status}</td>
            <td><img src="${student.image}" alt="loi"></td>
            <td><button class="btn btn-danger" onclick="addPointStudent(${student.id})">Add Point</button></td>
            <td><button class="btn btn-danger" onclick="editPointStudent(${student.id})">Edit Point</button></td>`;
}

function addPointStudent(id) {
    document.getElementById('chooseClassAddPoint').hidden = true;
    document.getElementById('showTableStudent').hidden = true;
    document.getElementById('formPoint').hidden = false;
    document.getElementById("formPoint-button").onclick = function () {
        addPointStudent1(id);
    }
}

function addPointStudent1(id) {
    let data = new FormData();
    let name = $('#nameModule').val();
    let practicePoint = $('#practicePoint').val();
    let theoreticalPoint = $('#theoreticalPoint').val();
    let newPoint = {
        name: name,
        practicePoint: practicePoint,
        theoreticalPoint: theoreticalPoint,
        student: {
            id: id,
        },
    };
    data.append("json", new Blob([JSON.stringify(newPoint)], {
        type: "application/json"
    }))
    $.ajax({
        type: "POST",
        data: data,
        processData: false,
        contentType: false,
        url: "http://localhost:8080/officers",
        success: function () {
            getPoint(id);
        }

    });
    event.preventDefault();
}

function editPointStudent(id) {
    document.getElementById('chooseClassAddPoint').hidden = true;
    document.getElementById('showTableStudent').hidden = true;
    document.getElementById("div-point-officer").hidden = true;
    indexStudent = id;
    editPointStudent1(id);
}

function editPointStudent1(idStudent) {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/officers/getPoint/${idStudent}`,
        //xử lý khi thành công
        success: function (data) {
            // hien thi danh sach o day
            let content = '<tr>\n' +
                '<th>Name</th>\n' +
                '<th>Practice Point</th>\n' +
                '<th>Theoretical Point</th>\n' +
                '<th colspan="2">Action</th>\n' +
                '</tr>';
            for (let i = 0; i < data.length; i++) {
                content += displayPointOfficerEdit(data[i]);
            }
            document.getElementById("pointEditListOfficer").innerHTML = content;
            document.getElementById("formPoint").hidden = true;
            document.getElementById("div-pointEdit-officer").hidden = false;

        }
    });
}

function displayPointOfficerEdit(point) {
    return `<tr><td><input  id="point-nameModule" type="text" value="${point.name}"></td>
            <td><input  id="point-practicePoint" type="text" value="${point.practicePoint}"></td>
            <td><input  id="point-theoreticalPoint" type="text" value="${point.theoreticalPoint}"></td>
            <td><button class="btn btn-danger" onclick="submitEdit(${point.id})">Submit Edit</button></td></tr>`;
}

function submitEdit(idPoint){
   let name =  $('#point-nameModule').val();
   let practicePoint = $('#point-practicePoint').val();
   let theoreticalPoint = $('#point-theoreticalPoint').val();
    let newPoint = {
        name: name,
        practicePoint: practicePoint,
        theoreticalPoint: theoreticalPoint,
        student: {id: indexStudent}
    }
    let data = new FormData;
    data.append("json", new Blob([JSON.stringify(newPoint)], {
        type: "application/json"
    }))
    $.ajax({
        type: "PUT",
        data: data,
        processData: false,
        contentType: false,
        url: `http://localhost:8080/officers/editPoint/${idPoint}`,
        success: function () {
            document.getElementById("div-pointEdit-officer").hidden = true;
           getPoint(indexStudent);
        }
    });
    event.preventDefault();

}

function statisticalPoint(){
}