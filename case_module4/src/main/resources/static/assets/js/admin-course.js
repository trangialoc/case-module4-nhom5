let indexCourse = 0;


function getCourseInfo() {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/courses`,
        success: function (data) {
            let content = '<tr>\n' +
                '<th>ID</th>\n' +
                '<th>No.Course</th>\n' +
                '<th>Name</th>\n' +
                '<th>Fee</th>\n' +
                '<th>Time Base</th>\n' +
                '<th colspan="2">Action</th>\n' +
                '</tr>';
            for (let i = 0; i < data.length; i++) {
                content += displayCourseInfo(data[i]);
            }
            document.getElementById("courseList").innerHTML = content;
            document.getElementById("formCourse").hidden = true;
        }
    });
}

function displayCourseInfo(course) {
    return `<tr><td>${course.id}</td>
            <td>${course.codeCourse}</td>
            <td>${course.name}</td>
            <td>${course.totalFee}</td>
            <td>${course.timeBase}</td>
            <td><button class="btn btn-danger" onclick="deleteCourse(${course.id})">Delete</button></td>
            <td><button class="btn btn-warning" onclick="editCourse(${course.id})">Edit</button></td></tr>`;
}

function displayFormCreateCourse() {
    document.getElementById("formCourse").reset()
    document.getElementById("formCourse").hidden = false;
    document.getElementById("formCourse-button").onclick = function () {
        addNewCourse();
    }
}

function addNewCourse() {
    let data = new FormData();
    let name = $('#nameCourse').val();
    let codeCourse = $('#codeCourse').val();
    let feeCourse = $('#feeCourse').val();
    let timeBase = $('#timeBase').val();

    let newCourse = {
        name: name,
        codeCourse: codeCourse,
        feeCourse: feeCourse,
        timeBase: timeBase
    };
    data.append("json", new Blob([JSON.stringify(newCourse)], {
            type: "application/json"
        })
    )
    $.ajax({
        type: "POST",
        data: data,
        processData: false,
        contentType: false,
        url: "http://localhost:8080/admin/courses",
        success: function () {
            getCourseByPage(0);
        }

    });
    event.preventDefault();
}

function deleteCourse(id) {
    $.ajax({
        type: "DELETE",
        url: `http://localhost:8080/admin/courses/${id}`,
        success: function () {
            getCourseByPage(0)
        }
    });
}

function searchCourse() {
    let searchCourse = document.getElementById("searchCourse").value;
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/admin/courses/search?search=${searchCourse}`,
        success: function (data) {
            let content = '<tr>\n' +
                '<th>ID</th>\n' +
                '<th>No.Course</th>\n' +
                '<th>Name</th>\n' +
                '<th>Fee</th>\n' +
                '<th>Time Base</th>\n' +
                '<th colspan="2">Action</th>\n' +
                '</tr>';
            for (let i = 0; i < data.length; i++) {
                content += displayCourseInfo(data[i]);
            }
            document.getElementById('courseList').innerHTML = content;
            document.getElementById("searchCourse").reset()
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

function displayCourseHeard() {
    return `<tr>
                <th>ID</th>
                <th>No.Course</th>
                <th>Name</th>
                <th>Fee</th>
                <th>Time Base</th>
               <th colspan="2">Action</th>
                </tr>`;
}


function getCourseByPage(page) {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/admin/courses/page?page=${page}`,
        //xử lý khi thành công
        success: function (data) {
            let array = data.content
            let content = displayCourseHeard();
            for (let i = 0; i < array.length; i++) {
                content += displayCourseInfo(array[i]);
            }
            document.getElementById("courseList").innerHTML = content;
            document.getElementById("displayPageCourse").innerHTML = displayPageCourse(data)
            document.getElementById("formCourse").hidden = true;
            if (data.pageable.pageNumber === 0) {
                document.getElementById("backup").hidden = true
            }
            if (data.pageable.pageNumber + 1 === data.totalPages) {
                document.getElementById("next").hidden = true
            }
        }
    });
}

function displayPageCourse(data) {
    return `<button class="btn btn-primary" id="backup" onclick="isPrevious(${data.pageable.pageNumber})">Previous</button>
    <span>${data.pageable.pageNumber + 1} | ${data.totalPages}</span>
    <button class="btn btn-primary" id="next" onclick="isNext(${data.pageable.pageNumber})">Next</button>`
}

function isPrevious(pageNumber) {
    getCourseByPage(pageNumber - 1)
}

function isNext(pageNumber) {
    getCourseByPage(pageNumber + 1)
}


function displayManagerCourse() {
    document.getElementById("manager-course").hidden = false;
    document.getElementById("manager-student").hidden = true;
    document.getElementById("manager-teacher").hidden = true;
    document.getElementById("manager-officer").hidden = true;
    document.getElementById("manager-user").hidden = true;

    getCourseByPage(0);
}

