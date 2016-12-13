var Validator = {
	//邮箱验证
	isEmail : function(s) {
		return this.test(s, '^(\.[a-zA-Z0-9_-]+)+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$');
		//return this.test(s, '^[-!#$%&\'*+\\./0-9=?A-Z^_`a-z{|}~]+@[-!#$%&\'*+\\/0-9=?A-Z^_`a-z{|}~]+\.[-!#$%&\'*+\\./0-9=?A-Z^_`a-z{|}~]+$');
	},
	//URL地址验证
	isAbsUrl : function(s) {
		return this.test(s, '^(news|telnet|nttp|file|http|ftp|https)://[-A-Za-z0-9\\.]+\\/?.*$');
	},
	//
	isSize : function(s) {
		return this.test(s, '^[0-9.]+(%|in|cm|mm|em|ex|pt|pc|px)?$');
	},
	//
	isId : function(s) {
		return this.test(s, '^[A-Za-z_]([A-Za-z0-9_])*$');
	},
	//手机号码验证
	isPhone : function (s) {
		return this.test(s,"^(13[0-9]|15[0-9]|18[0-9])\\d{8}$");
	},
	//电信号码验证
	isTyPhone : function(s){
		return this.test(s,"^(133|153|181|189|180)\\d{8}$");
	},
	//电话号码验证
	isTel:function(s){
		return this.test(s,"\\d{4}-\\d{8}|\\d{4}-\\d{7}|\\d{3}-\\d{8}");
	},
	//区号
	isTelcode:function(s){
		return this.test(s,"\\d{4}|\\d{3}");
	},
	//电话号
	isTelNum:function(s){
		return this.test(s,"\\d{7,8}");
	},
	//分机号
	isTelpart:function(s){
		return this.test(s,"\\d{4}|\\d{3}|\\d{2}|\\d{1}");
	},
	isZipCode:function(s){
		return this.test(s, "^[0-9]{5,7}");
	},
	//
	isFax:function(s){
		return this.test(s,"^[0-9]{7,11}");
	},
	//邮编验证
	isPostcode:function(s){
		//return this.test(s,"[1-9]\\d{5}(?!\\d)");
		return this.test(s,"^[0-9][0-9]{5}$");//开头不能为0，共6位
	},
	//身份证验证
	isIdCard:function(s){
		return this.test(s, "^([0-9]{6})(18|19|20)?([0-9]{2})([01][0-9])([0123][0-9])([0-9]{3})([0-9]|X|x)?$");
	},
	//字母
	isEn:function(s){
		return this.test(s,"^[a-zA-Z]");
	},
	//中文
	isChinaHan:function(s){
	   var reg = /[\u4e00-\u9fa5]+$/i;
	   return reg.test(s);
	},
	//是否为空
	isEmpty : function(s) {
		var nl, i;

		if (s.nodeName == 'SELECT' && s.selectedIndex < 1)
			return true;

		if (s.type == 'checkbox' && !s.checked)
			return true;

		if (s.type == 'radio') {
			for (i=0, nl = s.form.elements; i<nl.length; i++) {
				if (nl[i].type == "radio" && nl[i].name == s.name && nl[i].checked)
					return false;
			}

			return true;
		}

		return new RegExp('^\\s*$').test(s.nodeType == 1 ? s.value : s);
	},
	//是否是数字
	isNumber : function(s, d) {
		return !isNaN(s.nodeType == 1 ? s.value : s) && (!d || !this.test(s, '^-?[0-9]*\\.[0-9]*$'));
	},
	//是否是整数
	isInt:function(s){
		return !isNaN(s)&&this.test(s, "^[0-9]*$");
	},
	//是否为非法字符
	isByte:function(s){
	    return this.test(s,"[`~!@#$^&*()=|{}':;',\\[\\]._+<>/?%~！@#￥……&*（）——|{}【】‘；：”“\"'。，、？＂，．^\\s*]");
	},
	isNumorEn:function(s){
		return this.test(s,"^[a-zA-Z0-9_]{6,16}$");
	},
	//求字符长度
	byteLength:function(s){
		return s.replace(/[^\x00-\xff]/g, "xx" ).length;
	},
	//判断是否为IE
	isIE:function(){
		return /msie (\d+\.\d+)/i.test(navigator.userAgent);
	},
	//判断是否为火狐
	isFireFox:function(){
	    return /firefox\/(\d+\.\d+)/i.test(navigator.userAgent); 
	},
	//判断是否为谷歌
	isChrome:function(){
	    return /chrome\/(\d+\.\d+)/i.test(navigator.userAgent);
	},
	//控制文件上传大小，传id值的字符串，则清空文本框的值
	checkfileSize:function(obj,filesize,Id){ 
	    var flag = false;
        var fileSize = 0; 
		var isIE = /msie (\d+\.\d+)/i.test(navigator.userAgent); //如果是IE
    /*    try {      
            var filePath = obj.value;      
            var fileSystem = new ActiveXObject("Scripting.FileSystemObject");         
            fileSize = parseInt(fileSystem.getFile(filePath).size);//计算文件大小
            
          } catch(e) {     
            fileSize = obj.files[0].size;  //火狐,谷歌    
         } 
         */
		 fileSize = obj.files[0].size;  //火狐,谷歌    
         var size = fileSize / 1024; //单位为K
         if(size>filesize){
           alertDiv("文件大于"+filesize+"K,请修改后重新上传");
           $("#"+Id).val('');
           if(isIE&&Id.length>0){
             //兼容ie无法清空file里的值 
             //obj.select();document.execCommand('Delete');
           	  obj.outerHTML+='';
            
           }
          return flag;
         }
         else{
           flag = true;
          return flag;
        }
	   },
	test : function(s, p) {
		s = s.nodeType == 1 ? s.value : s;

		return s == '' || new RegExp(p).test(s);
	}
};

function validateIdCard(obj)
{
 var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙 江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖 北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西 藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国 外"};
  var iSum = 0;
 //var info = "";
 var strIDno = obj;
 var idCardLength = strIDno.length;
 if(!/^\d{17}(\d|x)$/i.test(strIDno)&&!/^\d{15}$/i.test(strIDno))
        return 1; //非法身份证号

 if(aCity[parseInt(strIDno.substr(0,2))]==null)
 return 2;// 非法地区

  // 15位身份证转换为18位
 if (idCardLength==15)
 {
    sBirthday = "19" + strIDno.substr(6,2) + "-" + Number(strIDno.substr(8,2)) + "-" + Number(strIDno.substr(10,2));
  var d = new Date(sBirthday.replace(/-/g,"/"));
  var dd = d.getFullYear().toString() + "-" + (d.getMonth()+1) + "-" + d.getDate();
  if(sBirthday != dd)
                return 3; //非法生日
              strIDno=strIDno.substring(0,6)+"19"+strIDno.substring(6,15);
              strIDno=strIDno+""+GetVerifyBit(strIDno);
 }

       // 判断是否大于2078年，小于1900年
       var year =strIDno.substring(6,10);
       if (year<1900 || year>2078 )
           return 3;//非法生日

    //18位身份证处理

   //在后面的运算中x相当于数字10,所以转换成a
    strIDno = strIDno.replace(/x$/i,"a");
   

  sBirthday=strIDno.substr(6,4)+"-"+Number(strIDno.substr(10,2))+"-"+Number(strIDno.substr(12,2));
  var d = new Date(sBirthday.replace(/-/g,"/"));
  if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))
                return 3; //非法生日
    // 身份证编码规范验证
  for(var i = 17;i>=0;i --)
   iSum += (Math.pow(2,i) % 11) * parseInt(strIDno.charAt(17 - i),11);
  
  if(iSum%11==9)
                return 0;
  if(iSum%11!=1)
                return 1;// 非法身份证号
   // 判断是否屏蔽身份证
    var words = new Array();
    words = new Array("11111119111111111","12121219121212121");

    for(var k=0;k<words.length;k++){
        if (strIDno.indexOf(words[k])!=-1){
            return 1;
        }
    }

 return 0;
}
// 计算身份证校验码，根据国家标准GB 11643-1999
function GetVerifyBit(id) {// 15位转18位中,计算校验位即最后一位
			var result;
			var nNum = eval(id.charAt(0) * 7 + id.charAt(1) * 9 + id.charAt(2) * 10
			   + id.charAt(3) * 5 + id.charAt(4) * 8 + id.charAt(5) * 4
			   + id.charAt(6) * 2 + id.charAt(7) * 1 + id.charAt(8) * 6
			   + id.charAt(9) * 3 + id.charAt(10) * 7 + id.charAt(11) * 9
			   + id.charAt(12) * 10 + id.charAt(13) * 5 + id.charAt(14) * 8
			   + id.charAt(15) * 4 + id.charAt(16) * 2);
			nNum = nNum % 11;
			switch (nNum) {
			case 0:
			  result = "1";
			  break;
			case 1:
			  result = "0";
			  break;
			case 2:
			  result = "X";
			  break;
			case 3:
			  result = "9";
			  break;
			case 4:
			  result = "8";
			  break;
			case 5:
			  result = "7";
			  break;
			case 6:
			  result = "6";
			  break;
			case 7:
			  result = "5";
			  break;
			case 8:
			  result = "4";
			  break;
			case 9:
			  result = "3";
			  break;
			case 10:
			  result = "2";
			  break;
			}
			return result;
		}

