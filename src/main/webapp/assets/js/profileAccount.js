var profileAccount = function (){

    jQuery.fn.serializeObject = function () {
        var formData = {};
        var formArray = this.serializeArray();
        for(var i = 0, n = formArray.length; i < n; ++i){
            formData[formArray[i].name] = formArray[i].value;
        }
        return formData;
    };
    var handleUserList = function (){
        var data ={}
        data.type = "getUsersList"
        // $.post("/UserInfo",data,function (res){
        //     var list = res.Data.UserList
        //
        //     var html = ""
        //     console.log(1111)
        //     for(var i = 0; i < list.length; ++i)
        //     {
        //         console.log(1111)
        //         html += "<tr>"
        //         html += "<td>" + list[i].user_name + "</td>"
        //         html += "<td" + list[i].nickname + "</td>"
        //         html += "<td>" + list[i].role_type + "</td>"
        //         html += "<td>" + list[i].occupation + "</td>"
        //         html += "<td>" + list[i].mobile_number + "</td>"
        //         html += "<td>" + list[i].email + "</td>"
        //         html += "<td>" + list[i].create_time + "</td>"
        //         html += "<td>" + "<a class=\"edit\" href=\"javascript:;\">Edit </a>" + "</td>"
        //         html += "<td>" + "<a class=\"delete\" href=\"javascript:;\">Delete </a>" + "</td>"
        //         html += "</tr>"
        //     }
        //     console.log(html)
        //     console.log(1111)
        //     $("#usersInfo").append(html)
        // })
    }

    var handlePersonalInfoVerify = function (){
        $("#PersonalInfo").validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules:{
                Name:{
                    required: true
                },
                NickName:{
                    required: false
                },
                Email:{
                    required:true,
                    email: true
                },
                MobileNumber:{
                    required:false
                },
                role:{
                    required:false
                },
                About:{
                    required:false
                }
            },
            messages:{
                Name:{
                    required: "名字不能为空"
                },
                Email:{
                    required:"邮箱不能为空",
                    email: "请输入正确格式的邮箱"
                }
            },
            highlight: function(element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },
            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },
            errorPlacement: function(error, element) {
                error.insertAfter(element)
            },
            submitHandler: function(form) {

                var data = {}
                data = $("#PersonalInfo").serializeObject()
                data.type = "setUserInfo";
                console.log(data)
                $.post("/UserInfo",data,function (res){
                    console.log(res)
                })
            },

        })
    }

    var handleResetPassword = function ()
    {
        $("#repassword").validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                oldPassword:{
                    required:true,
                },
                newPassword:{
                    required:true,
                },
                rnewPassword:{
                    equalTo: "#newPassword"
                }
            },
            messages: {
                oldPassword:{
                    required:"请输入旧密码！"
                },
                newPassword:{
                    required:"请输入新密码！"
                },
                rnewPassword:{
                    equalTo: "两次输入密码必须相同！"
                }
            },
            highlight: function(element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },
            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },
            errorPlacement: function(error, element) {
                error.insertAfter(element)
            },
            submitHandler:function (form)
            {
                var data = {}
                data = $("#repassword").serializeObject()
                data.type = "resetPassword";
                console.log(data)
                $.post("/LoginControl",data,function (res){
                    console.log(res)
                    if(res.data.repasswordCode == 0)
                        alert("修改成功！")
                    else{
                        alert("修改失败")
                    }
                })
            }

        })
    }
    return{
        init: function () {
            handlePersonalInfoVerify();
            handleResetPassword();
            handleUserList();
        }
    }

}()