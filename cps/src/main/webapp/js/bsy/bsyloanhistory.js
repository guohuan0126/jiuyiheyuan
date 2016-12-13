$(function(){
	
	
});


function searchLoan(){
	alert(123);
	var loanId=$("#loanId").val();
	var loanTimeBegin=$("#loanTimeBegin").val();
	var loanTimeEnd=$("#loanTimeEnd").val();
	var loanStatus=$("#loanStatus").val();
	var loanName=$("#loanName").val();
	var pushTimeBegin=$("#pushTimeBegin").val();
	var pushtimeEnd=$("#pushtimeEnd").val();
	var loanType=$("#loanType").val();
	var loan
	if(loanId!="" && lonaId!=undefined){
		loan={
			loanId:loanId	
		}
	}
	if(loanTimeBegin!="" && loanTimeBegin){
		loan={
				loanTimeBegin:loanTimeBegin	
			}
	}
	
	alert(JSON.stringify(loan));
}

