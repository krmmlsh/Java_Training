	$(function() {
		$("form[name='computerForm']").validate({
			rules : {
				computerName : {
					required : true,
					minlength : 4
				},
				introduced : {
					regex : /^((0[1-9]|[12]\d|3[01])(\/|-)(0[1-9]|1[012])(\/|-)\d\d(\d\d)?|\d\d\d\d-(0[1-9]|1[012])-(0[1-9]|[12]\d|3[01]))$/
				},
				discontinued : {
					regex : /^((0[1-9]|[12]\d|3[01])(\/|-)(0[1-9]|1[012])(\/|-)\d\d(\d\d)?|\d\d\d\d-(0[1-9]|1[012])-(0[1-9]|[12]\d|3[01]))$/
				}
			},
			messages : {
				computerName : "Please enter a valid name",
				introduced : "Please enter a valid date",
				discontinued : "Please enter a valid date"
				
			},
		    submitHandler: function(form) {
		        form.submit();
		    },
		    focusCleanup: true,
		    onkeyup: true,
		});
	});
	
	$.validator.addMethod(
	        "regex",
	        function(value, element, regexp) {
	            var re = new RegExp(regexp);
	            return this.optional(element) || re.test(value);
	        },
	        "Please check your input."
	);