
let indexTeacherDiary = 0;
let indexStudentDiary = 0;
let indexClassesDiary = 0;

function findIDTeacher(){
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/teachers/showTeacherID",
        success: function (data) {
            indexTeacherDiary = data.id
        }
    });
}
function findIDClasses(){
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/teachers/showClassID",
        success: function (data) {
            indexTeacherDiary = data.id
        }
    });
}

function showStudentByClasses(){
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/teachers/showStudent`,
        success: function (data) {
            if(data===null){
                indexClassesDiary = 0;
            }else {
                indexClassesDiary = data[0].classes.id
            }
            let content = '<tr>\n' +
                '<th>Full Name</th>\n' +
                '<th>Birth</th>\n' +
                '<th>Gender</th>\n' +
                '<th>Email</th>\n' +
                '<th>Username</th>\n' +
                '<th>Class</th>\n' +
                '<th>Course</th>\n' +
                '<th>Status</th>\n' +
                '<th colspan="2">Action</th>\n' +
                '</tr>';
            for (let i = 0; i < data.length; i++) {
                content += displayStudent1(data[i]);
            }

            document.getElementById("studentListByClass").innerHTML = content;
            document.getElementById("showTableStudentByClass").hidden = false;
            document.getElementById("showTableTeacherByClass").hidden = true;
            document.getElementById("diaryClass-textArea").hidden = true;
            document.getElementById("diaryStudent-textArea").hidden = true;
            document.getElementById("div-detailStudent").hidden = true;

        }
    });
}

function displayStudent1(student) {
    return `<tr><td>${student.appUser.fullName}</td><td>${student.appUser.birth}</td>
            <td>${student.gender}</td>
            <td>${student.appUser.email}</td><td>${student.appUser.username}</td>
            <td>${student.classes.name}</td> <td>${student.course.name}</td>
            <td>${student.statusStudent.status}</td>
            <td><button class="btn btn-danger" onclick="viewStudentDetail(${student.id})">View</button></td>`;
}

function showTeacherByClass(){
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/teachers/showTeacher`,
        success: function (data) {
            let content = '<tr>\n' +
                '<th>Name</th>\n' +
                '<th>Phone Number</th>\n' +
                '<th>Email</th>\n' +
                '<th>Class</th>\n' +
                '</tr>';
            for (let i = 0; i < data.length; i++) {
                content += displayTeacherByClass(data[i]);
            }

            document.getElementById("teacherListByClass").innerHTML = content;
            document.getElementById("showTableTeacherByClass").hidden = false;
            document.getElementById("showTableStudentByClass").hidden = true;
            document.getElementById("div-detailStudent").hidden = true;
        }
    });
}

function displayTeacherByClass(teacher) {
    return `<tr><td>${teacher.appUser.fullName}</td>
            <td>${teacher.appUser.phoneNumber}</td>
            <td>${teacher.appUser.email}</td>
            <td>${teacher.classes.name}</td></tr>
          `;

}

function displayFormCreateDiaryClass(){
    document.getElementById('diaryClass-textArea').hidden = false;
    document.getElementById('form-button-diaryClass').onclick = function (){
        createDiaryClass();
    };

}

function createDiaryClass(){
    let localDate = new Date();
    let note = $('#diaryClass-note').val();
    let newNote = {
        localDate: localDate,
        note: note,
        teacher:{id: indexTeacherDiary},
        classes:{id: indexClassesDiary}
    }
    let data = new FormData();
    data.append("json", new Blob([JSON.stringify(newNote)], {
        type: "application/json"
    }))
    $.ajax({
        type: "POST",
        data: data,
        processData: false,
        contentType: false,
        url: "http://localhost:8080/teachers/createDiaryClass",
        success: function () {
            showStudentByClasses();
            document.getElementById('diaryClass-textArea').hidden = true;
        }
    });
    event.preventDefault();
}



function viewStudentDetail(id){
    $.ajax({
        type: "GET",
        url:`http://localhost:8080/teachers/detailStudent/${id}`,
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

                content += displayDetailStudent(data);
                indexStudentDiary = data.id


            document.getElementById("detailStudent").innerHTML = content;
            document.getElementById("div-detailStudent").hidden = false;
            document.getElementById("showTableStudentByClass").hidden = true;
            document.getElementById("showTableTeacherByClass").hidden = true;
        }
    });
    event.preventDefault();


}

function displayDetailStudent(student){
    return `<tr><td>${student.appUser.fullName}</td><td>${student.appUser.birth}</td>
            <td>${student.gender}</td><td>${student.appUser.phoneNumber}</td>
            <td>${student.appUser.email}</td><td>${student.appUser.identify}</td>
            <td><img src="${student.image}" alt="loi"></td>
            <td><button class="btn btn-warning" onclick="viewDiaryStudent(${student.id})">View Note</button></td>
            <td><button class="btn btn-warning" onclick="displayFormCreateDiaryStudent(${student.id})">Create Note</button></td></tr>`;
}

function displayFormCreateDiaryStudent(){
    document.getElementById('diaryStudent-textArea').hidden = false;
    document.getElementById('form-button-diaryStudent').onclick = function (){
        addNoteStudent();
    };

}

function addNoteStudent(id){
    let localDate = new Date();
    let note = $('#diaryStudent-note').val();
    let newNoteStudent = {
        localDate: localDate,
        note: note,
        teacher:{id: indexTeacherDiary},
        student:{id:indexStudentDiary}
    }
    let data = new FormData();
    data.append("json", new Blob([JSON.stringify(newNoteStudent)], {
        type: "application/json"
    }))
    $.ajax({
        type: "POST",
        data: data,
        processData: false,
        contentType: false,
        url: "http://localhost:8080/teachers/createDiaryStudent",
        success: function () {
            viewStudentDetail(id);
            document.getElementById('diaryStudent-textArea').hidden = true;
        }
    });
    event.preventDefault();
}

function viewDiaryStudent(){

}

findIDTeacher()