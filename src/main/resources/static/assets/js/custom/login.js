$(document).ready(function() {

$('#login').on('click', function() {	
		
		if($('#username').val().trim() != '' && $('#password').val().trim() != ''){

		var form = new FormData(); 
		form.append('username',$('#username').val());
		form.append('password',$('#password').val());
		$.ajax({
	       url: 'login.php',
	       cache: false,
	       contentType: false,
	       processData: false,
	       data: form,                         
	       type: 'post',
	       success: function(response){
	        	if(response == 'Success'){
	        		window.location = 'dashboard.php';
	        	}
	        	else{
	        		alert("Wrong Credentials");
	        	}
	       }
	   });

	}
	else{
		alert("Fields Can't Be Empty");
	}

	});




$('#logout').on('click', function() {	
		var form = new FormData(); 
		form.append('logout','logout');
		$.ajax({
	       url: 'login.php',
	       cache: false,
	       contentType: false,
	       processData: false,
	       data: form,                         
	       type: 'post',
	       success: function(response){
	        	window.location = "index.php";
	       }
	   });
	});

});