	$(function() {
		$("form[name='computerForm']").validate({
			rules : {
				computerName : {
					required : true,
					minlength : 4
				},
				introduced : {
					regex : /^((\d?\d)(\/|-)\d\d(\/|-)\d\d(\d\d)?|\d\d\d\d-\d\d-\d\d)$/
				},
				discontinued : {
					regex : /^((\d?\d)(\/|-)\d\d(\/|-)\d\d(\d\d)?|\d\d\d\d-\d\d-\d\d)$/
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
		    onkeyup: false,
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