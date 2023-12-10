<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<!--
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.3.5
Version: 3.3.0
Author: KeenThemes
Website: http://www.keenthemes.com/
Contact: support@keenthemes.com
Follow: www.twitter.com/keenthemes
Like: www.facebook.com/keenthemes
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
License: You must have a valid license purchased only from themeforest(the above link) in order to legally use the theme for your project.
-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
  <meta charset="utf-8"/>
  <title>Metronic | Login Options - Login Form 2</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
  <meta http-equiv="Content-type" content="text/html; charset=utf-8">
  <meta content="" name="description"/>
  <meta content="" name="author"/>
  <!-- BEGIN GLOBAL MANDATORY STYLES -->
  <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/>
  <link href="../../assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
  <link href="../../assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
  <link href="../../assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
  <link href="../../assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
  <!-- END GLOBAL MANDATORY STYLES -->
  <!-- BEGIN PAGE LEVEL STYLES -->
  <link href="../../assets/admin/pages/css/login2.css" rel="stylesheet" type="text/css"/>
  <!-- END PAGE LEVEL SCRIPTS -->
  <!-- BEGIN THEME STYLES -->
  <link href="../../assets/global/css/components-md.css" id="style_components" rel="stylesheet" type="text/css"/>
  <link href="../../assets/global/css/plugins-md.css" rel="stylesheet" type="text/css"/>
  <link href="../../assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
  <link href="../../assets/admin/layout/css/themes/darkblue.css" rel="stylesheet" type="text/css" id="style_color"/>
  <link href="../../assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css"/>
  <!-- END THEME STYLES -->
  <link rel="shortcut icon" href="favicon.ico"/>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-md login" onbeforeunload="<%if(session.getAttribute("remember") == null){session.invalidate();}%>">
<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
<div class="menu-toggler sidebar-toggler">
</div>
<!-- END SIDEBAR TOGGLER BUTTON -->
<!-- BEGIN LOGO -->
<div class="logo">
  <a href="login.jsp">
    <img src="../../assets/admin/layout/img/logo-big-white.png" style="height: 17px;" alt=""/>
  </a>
</div>
<!-- END LOGO -->
<!-- BEGIN LOGIN -->

<div class="content">
  <!-- BEGIN LOGIN FORM -->
  <form class="login-form"4>
    <input type="hidden" name="type" value="login"/>
    <div class="form-title">
      <span class="form-title">Welcome.</span>
      <span class="form-subtitle">Please login.</span>
    </div>
    <div class="alert alert-danger display-hide" id="error">
      <button class="close" data-close="alert"></button>
      <span>
			Please enter right user name or password. </span>
    </div>
    <div class="form-group">
      <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
      <label class="control-label visible-ie8 visible-ie9">Username</label>
      <input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="Username" name="username" id="username"/>
    </div>
    <div class="form-group">
      <label class="control-label visible-ie8 visible-ie9">Password</label>
      <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="Password" name="password" id="password"/>
    </div>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary btn-block uppercase">Login</button>
    </div>
    <div class="form-actions">
      <div class="pull-left">
        <label class="rememberme check">
          <input type="checkbox" name="remember" value="1"/>Remember me</label>
      </div>
      <div class="pull-right forget-password-block">
        <a href="javascript:;" id="forget-password" class="forget-password">Forgot Password?</a>
      </div>
    </div>
    <div class="login-options">
      <h4 class="pull-left">Or login with</h4>
      <ul class="social-icons pull-right">
        <li>
          <a class="social-icon-color facebook" data-original-title="facebook" href="javascript:;"></a>
        </li>
        <li>
          <a class="social-icon-color twitter" data-original-title="Twitter" href="javascript:;"></a>
        </li>
        <li>
          <a class="social-icon-color googleplus" data-original-title="Goole Plus" href="javascript:;"></a>
        </li>
        <li>
          <a class="social-icon-color linkedin" data-original-title="Linkedin" href="javascript:;"></a>
        </li>
      </ul>
    </div>
    <div class="create-account">
      <p>
        <a href="javascript:;" id="register-btn">Create an account</a>
      </p>
    </div>
  </form>
  <!-- END LOGIN FORM -->
  <!-- BEGIN FORGOT PASSWORD FORMS -->
  <form class="forget-form" >
    <input type="hidden" name="type" value="send"/>
    <div class="form-title">
      <span class="form-title">Forget Password ?</span>
      <span class="form-subtitle">Enter your e-mail to reset it.</span>
    </div>
    <div class="form-group">
      <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="User" name="username" id="user_name"/>
    </div>
    <div class="form-group">
      <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Email" name="email"/>
    </div>
    <div class="alert alert-danger display-hide" id="erroremail">
      <button class="close" data-close="alert"></button>
      <span>
			Please enter right username or email. </span>
    </div>
    <div class="form-actions">
      <button type="button" id="back-btn" class="btn btn-default">Back</button>
      <button type="submit" class="btn btn-primary uppercase pull-right">Submit</button>
    </div>
  </form>
  <form class="verify-form"  style="display: none">
    <input type="hidden" name="type" value="verify"/>
    <div class="form-title">
      <span class="form-title">Email send successfully to your host</span>
      <span class="form-subtitle">Enter your verify code to verify it.</span>
    </div>
    <div class="form-group" style="display: inline-block">
      <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="verifycode"/>
    </div>
    <button type="button" id="resend" class="btn btn-default" style="display: inline-block">resend</button>
    <div class="alert alert-danger display-hide" id="errorcode">
      <button class="close" data-close="alert"></button>
      <span>
			Verify code error,please resend code. </span>
    </div>
    <div class="form-actions">
      <button type="button" id="back" class="btn btn-default">Back</button>
      <button type="submit" class="btn btn-primary uppercase pull-right">Submit</button>
    </div>
  </form>
  <form class="resetPassword-form"  style="display: none">
    <input type="hidden" name="type" value="resetPassword"/>
    <div class="form-title">
      <span class="form-title">Email confirmed successfully,please reset your password</span>
      <span class="form-subtitle">Enter your new password.</span>
    </div>
    <div class="form-group">
      <input class="form-control placeholder-no-fix" id="passwordReset" type="text" autocomplete="off" placeholder="" name="password"/>
    </div>
    <div class="form-group">
      <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="" name="rpassword"/>
    </div>
    <div class="form-actions">
      <button type="button" id="backtoverify" class="btn btn-default">Back</button>
      <button type="submit" class="btn btn-primary uppercase pull-right">Submit</button>
    </div>
  </form>
  <!-- END FORGOT PASSWORD FORMS -->
  <!-- BEGIN REGISTRATION FORM -->
  <form class="register-form">
    <input type="hidden" name="type" value="register"/>
    <div class="form-title">
      <span class="form-title">Sign Up</span>
    </div>
    <p class="hint">
      Enter your personal details below:
    </p>
    <div class="form-group">
      <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
      <label class="control-label visible-ie8 visible-ie9">Email</label>
      <input class="form-control placeholder-no-fix" type="text" placeholder="Email" name="email"/>
    </div>
    <div class="form-group">
      <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
      <label class="control-label visible-ie8 visible-ie9">nickname</label>
      <input class="form-control placeholder-no-fix" type="text" placeholder="nickname" name="nickname"/>
    </div>
    <div class="alert alert-danger display-hide" id="errorphone">
      <button class="close" data-close="alert"></button>
      <span>
			Please enter right phone number. </span>
    </div>
    <div class="form-group">
      <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
      <label class="control-label visible-ie8 visible-ie9">phone</label>
      <input class="form-control placeholder-no-fix" type="text" placeholder="phone" name="phone" id="phone_number"/>
    </div>
    <p class="hint">
      Enter your account details below:
    </p>
    <div class="alert alert-danger display-hide" id="errorUser">
      <button class="close" data-close="alert"></button>
      <span>
			Username already exist. </span>
    </div>
    <div class="form-group">
      <label class="control-label visible-ie8 visible-ie9">Username</label>
      <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Username" name="username"/>
    </div>
    <div class="form-group">
      <label class="control-label visible-ie8 visible-ie9">Password</label>
      <input class="form-control placeholder-no-fix" type="password" autocomplete="off" id="register_password" placeholder="Password" name="password"/>
    </div>
    <div class="form-group">
      <label class="control-label visible-ie8 visible-ie9">Re-type Your Password</label>
      <input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="Re-type Your Password" name="rpassword"/>
    </div>
    <div class="form-group margin-top-20 margin-bottom-20">
      <label class="check">
        <input type="checkbox" name="tnc"/>
        <span class="loginblue-font">I agree to the </span>
        <a href="javascript:;" class="loginblue-link">Terms of Service</a>
        <span class="loginblue-font">and</span>
        <a href="javascript:;" class="loginblue-link">Privacy Policy </a>
      </label>
      <div id="register_tnc_error">
      </div>
    </div>
    <div class="form-actions">
      <button type="button" id="register-back-btn" class="btn btn-default">Back</button>
      <button type="submit" id="register-submit-btn" class="btn btn-primary uppercase pull-right">Submit</button>
    </div>
  </form>
  <!-- END REGISTRATION FORM -->
</div>
<div class="copyright hide">
  2014 © Metronic. Admin Dashboard Template.
</div>
<!-- END LOGIN -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="../../assets/global/plugins/respond.min.js"></script>
<script src="../../assets/global/plugins/excanvas.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<![endif]-->
<script src="../../assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="../../assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="../../assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="../../assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="../../assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="../../assets/admin/pages/scripts/login.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script>
  jQuery(document).ready(function() {
    Metronic.init(); // init metronic core components
    Layout.init(); // init current layout
    Login.init();
    Demo.init();
  });
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>
