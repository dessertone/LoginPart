var Login = function() {

    jQuery.fn.serializeObject = function () {
        var formData = {};
        var formArray = this.serializeArray();
        for(var i = 0, n = formArray.length; i < n; ++i){
            formData[formArray[i].name] = formArray[i].value;
        }
        return formData;
    };
    var handleLogin = function() {

        $('.login-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input\
            onsubmit:true,
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                },
                remember: {
                    required: false
                }
            },

            messages: {
                username: {
                    required: "Username is required."
                },
                password: {
                    required: "Password is required."
                }
            },

            invalidHandler: function(event, validator) { //display error alert on form submit   
                $('.alert-danger', $('.login-form')).show();
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
                var data={};
                console.log($(".login-form").serializeObject())
                data = $(".login-form").serializeObject()

                $.post("/LoginControl",data,function (json)
                {
                    console.log(json)
                    // 密码正确
                    if(json.data.correct == 0)
                    {
                        window.location.href = "../userManage/extra_profile_account.jsp"
                        $("#error").hide()
                    }
                    else
                    {
                        $("#error").show()

                    }
                })
            }
        });

        $('.login-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.login-form').validate().form()) {
                    $('.login-form').submit(); //form validation success, call ajax form submit
                }
                return false;
            }
        });


    }
    var handleresetPasswordCode = function (){
        $(".resetPassword-form").validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                password: {
                    required: true
                },
                rpassword: {
                    equalTo: "#passwordReset"
                }
            },
            messages:{
                password:{
                    required:"password cannot be void"
                },
                rpassword:{
                    equalTo: "The password must be the same when entered twice"
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
            submitHandler:function (form){
                var data={};
                data = $(".resetPassword-form").serializeObject()
                data.username = $(".forget-form #user_name").val()
                console.log(data)
                $.post("/LoginControl",data,function (res){
                    if(res.data.repasswordCode == 0)
                    {
                        alert("修改成功！")
                    }
                })
            },
        })
        jQuery('#backtoverify').click(function() {
            jQuery('.resetPassword-form').hide();
            jQuery('.verify-form').show();
        });
    }
    var handleverifyCode = function (){
        $(".verify-form").validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                verifycode: {
                    required: true
                },
            },
            messages:{
                verifycode:{
                    required:"verify code cannot be void"
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
            submitHandler:function (form){
                var data={};
                console.log($(".verify-form").serializeObject())
                data = $(".verify-form").serializeObject()
                $.post("/LoginControl",data,function (res){
                    if(res.data.VerifyCode == 0)
                    {
                        $("#errorcode").hide()
                        jQuery('.verify-form').hide();
                        jQuery('.resetPassword-form').show();
                    }
                    else if(res.data.VerifyCode == -1)
                    {
                        $("#errorcode").show()
                    }
                })
            },
        })
        jQuery('#back').click(function() {
            jQuery('.forget-form').show();
            jQuery('.verify-form').hide();
        });
        $("#resend").click(function (){
            var data = {}
            data = $(".forget-form").serializeObject()
            $.post("/LoginControl",data,function (res){
                if(res.data.VerifyCode == 0)
                {
                    $("#errorcode").hide()
                    jQuery('.verify-form').hide();
                    jQuery('.resetPassword-form').show();
                }
                else if(res.data.VerifyCode == -1)
                {
                    $("#errorcode").show()
                }
            })
            // 计时冷却
            var num = 60;
            $("#resend").text(num + "s")
            $("#resend").attr("disabled","true")
            $("#resend").css("cursor","auto")
            var interval = setInterval(function (){
                num--;
                if(num <= 0)
                {
                    $("#resend").text("resend")
                    $("#resend").removeAttr("disabled")
                    $("#resend").css("cursor","pointer")
                    clearInterval(interval)
                }
                else
                {
                    $("#resend").text(num + "s")
                }
            },1000)
        })
    }
    var handleForgetPassword = function() {
        $('.forget-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                email: {
                    required: true,
                    email: true
                },
                username:{
                    required:true
                }
            },

            messages: {
                email: {
                    required: "Email is required."
                },
                username:{
                    required:"username is required"
                }
            },

            invalidHandler: function(event, validator) { //display error alert on form submit   

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
                var data={};
                console.log($(".forget-form").serializeObject())
                data = $(".forget-form").serializeObject()
                $.post("/LoginControl",data,function (res){
                    var data = res.data;
                    console.log(res)
                    if(data.EmailPass == 0)
                    {
                        $("#erroremail").hide()
                        jQuery('.verify-form').show();
                        jQuery('.forget-form').hide();
                    }
                    if(data.EmailPass == -1)
                    {
                        $("#erroremail").show()
                    }
                })

            }
        });

        $('.forget-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.forget-form').validate().form()) {
                    $('.forget-form').submit();
                }
                return false;
            }
        });

        jQuery('#forget-password').click(function() {
            jQuery('.login-form').hide();
            jQuery('.forget-form').show();
        });

        jQuery('#back-btn').click(function() {
            jQuery('.login-form').show();
            jQuery('.forget-form').hide();
        });

    }

    var handleRegister = function() {

        function format(state) {
            if (!state.id) return state.text; // optgroup
            return "<img class='flag' src='../../assets/global/img/flags/" + state.id.toLowerCase() + ".png'/>&nbsp;&nbsp;" + state.text;
        }

        if (jQuery().select2) {
	        $("#select2_sample4").select2({
	            placeholder: '<i class="fa fa-map-marker"></i>&nbsp;Select a Country',
	            allowClear: true,
	            formatResult: format,
	            formatSelection: format,
	            escapeMarkup: function(m) {
	                return m;
	            }
	        });


	        $('#select2_sample4').change(function() {
	            $('.register-form').validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input
	        });
    	}

        $('.register-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                fullname: {
                    required: true
                },
                email: {
                    required: true,
                    email: true
                },
                nickname:{
                    required: true
                },
                phone:{
                    required: true
                },
                username: {
                    required: true
                },
                password: {
                    required: true
                },
                rpassword: {
                    equalTo: "#register_password"
                },
                tnc: {
                    required: true
                }
            },

            messages: { // custom messages for radio buttons and checkboxes
                tnc: {
                    required: "Please accept TNC first."
                }
            },

            invalidHandler: function(event, validator) { //display error alert on form submit   

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
                if (element.attr("name") == "tnc") { // insert checkbox errors after the container                  
                    error.insertAfter($('#register_tnc_error'));
                } else if (element.closest('.input-icon').size() === 1) {
                    error.insertAfter(element.closest('.input-icon'));
                } else {
                    error.insertAfter(element);
                }
            },

            submitHandler: function(form) {
                if(!$.isNumeric($("#phone_number").val()))
                {
                    $("#errorphone").show()
                    return;
                }
                else
                {
                    $("#errorphone").hide()
                }
                var data = $(".register-form").serializeObject()
                $.post("/LoginControl",data,function (json)
                {
                    if(json.data.userAlreadyExist == true)
                    {
                        $("#errorUser").show()
                    }
                    else
                    {
                        $("#errorUser").hide()
                        window.location.href = "../userManage/extra_profile_account.jsp"
                    }
                })
            }
        });

        $('.register-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.register-form').validate().form()) {
                    $('.register-form').submit();
                }
                return false;
            }
        });

        jQuery('#register-btn').click(function() {
            jQuery('.login-form').hide();
            jQuery('.register-form').show();
        });

        jQuery('#register-back-btn').click(function() {
            jQuery('.login-form').show();
            jQuery('.register-form').hide();
        });
    }

    return {
        //main function to initiate the module
        init: function() {
            handleLogin();
            handleForgetPassword();
            handleRegister();
            handleverifyCode();
            handleresetPasswordCode();
        }

    };

}();