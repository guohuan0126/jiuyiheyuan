jQuery(".select").select2({
							width : '100%',
							minimumResultsForSearch : -1
						});
					
						jQuery("#basicForm").validate(
								{
									highlight : function(element) {
										jQuery(element).closest('.form-group').removeClass(
												'has-success').addClass('has-error');
									},
									success : function(element) {
										jQuery(element).closest('.form-group').removeClass(
												'has-error');
									}
								});
																
						jQuery("#basicForm3").validate(
								{
									highlight : function(element) {
										jQuery(element).closest('.form-group').removeClass(
												'has-success').addClass('has-error');
									},
									success : function(element) {
										jQuery(element).closest('.form-group').removeClass(
												'has-error');
									}
								});
			
						jQuery("#basicForm4").validate(
								{
									highlight : function(element) {
										jQuery(element).closest('.form-group').removeClass(
												'has-success').addClass('has-error');
									},
									success : function(element) {
										jQuery(element).closest('.form-group').removeClass(
												'has-error');
									}
								});
			
			                    