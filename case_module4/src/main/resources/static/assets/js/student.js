

function displayManagerStudentInfo(){
    document.getElementById("displayInformationStudent").hidden= false;
    document.getElementById("feeInfo").hidden= true;
    document.getElementById("div-point").hidden= true;
}


function displayFeeInformation() {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/students/feeInfo`,
        success: function (data) {
            // hien thi danh sach o day
            $('#feeTotal').val(data.feeTotal);
            $('#feePaid').val(data.feePaid);
            $('#remainingPay').val(data.remainingPay);


            document.getElementById("feeInfo").hidden = false;
            document.getElementById("displayInformationStudent").hidden = true;
            document.getElementById("div-point").hidden = true;
        }
    });
}

function displayPoint() {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/students/point`,
        //xử lý khi thành công
        success: function (data) {
            // hien thi danh sach o day
            let content = '<tr>\n' +
                '<th>Name</th>\n' +
                '<th>Practice Point</th>\n' +
                '<th>Theoretical Point</th>\n' +
                '</tr>';
            for (let i = 0; i < data.length; i++) {
                content += displayPointData(data[i]);
            }
            document.getElementById("pointList").innerHTML = content;
            document.getElementById("div-point").hidden = false;
            document.getElementById("displayInformationStudent").hidden = true;
            document.getElementById("feeInfo").hidden = true;

        }
    });
}

function displayPointData(point) {
    return `<tr><td>${point.name}</td>
            <td>${point.practicePoint}</td>
            <td>${point.theoreticalPoint}</td></tr>`;
}


