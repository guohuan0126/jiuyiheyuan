/**
 * 验证功能
 * @returns
 */
//var formValidate = (function(){
//判断是否为空
var isNull = function ( str ){
	if ( str == "" ) return true;
	var regu = "^[ ]+$";
	var re = new RegExp(regu);
	return re.test(str);
};


//验证手机号是否正确
var checkMobile = function(s){
	var regu =/^0?1[3|4|5|7|8][0-9]\d{8}$/;
	var re = new RegExp(regu);
	if (re.test(s)) {
	        return true;
	    }else{
	       return false;
	    }
};

//验证密码是否正确
var checkPass = function(s){
	var regu =/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/ig;
	var re = new RegExp(regu);
	if (re.test(s)) {
	return true;
	}else{
	return false;
	}
};

//验证工号是否正确
var checkEmployeeCode = function(s){
	var regu =/^(＼d+)$|([a-zA-Z]+)$|([0-9a-zA-Z]+)$/;
	var re = new RegExp(regu);
	if (re.test(s)) {
	        return true;
	    }else{
	       return false;
	    }
};
/*
用途：检查输入字符串是否符合正整数格式
输入：
s：字符串
返回：
如果通过验证返回true,否则返回false
*/
var isNumber = function ( s ){
	var regu = "^[0-9]+$";
	var re = new RegExp(regu);
	if (s.search(re) != -1) {
	return true;
	} else {
	return false;
	}
};
/*
用途：检查输入字符串是否只由英文字母和数字组成
输入：
s：字符串
返回：
如果通过验证返回true,否则返回false
*/
var isNumberOrLetter = function ( s ){//判断是否是数字或字母
var regu = "^[0-9a-zA-Z]+$";
var re = new RegExp(regu);
	if (re.test(s)) {
	   return true;
	}else{
	  return false;
	}
};

/*
用途：检查输入对象的值是否符合E-Mail格式
输入：str 输入的字符串
返回：如果通过验证返回true,否则返回false
*/
var isEmail = function ( str ){
var myReg = /^[-_A-Za-z0-9]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;
if(myReg.test(str)) return true;
return false;
};

/**
* 检查输入的字符是否具有特殊字符
* 输入:str  字符串
* 返回:true 或 flase; true表示包含特殊字符
* 主要用于注册信息的时候验证
*/
var checkQuote = function (str) {
    var items = new Array( "!", "%", "&", "*", "[", "]", "(", ")", " ", "=");
    items.push( "|", "\\", "<", ">", "?", "/", "<<", ">>", "||", "//");
    items.push("admin", "administrators", "administrator", "管理员", "系统管理员");
    items.push("select", "delete", "update", "insert", "create", "drop", "alter", "trancate");
    str = str.toLowerCase();
    for (var i = 0; i < items.length; i++) {
        if (str.indexOf(items[i]) >= 0) {
            return true;
        }
    }
    return false;
};

/**
* 检查输入的身份证号是否正确
* 输入:str  字符串
*  返回:true 或 flase; true表示格式正确
*/
var checkCard = function (str) {

	var re = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
	if (re.test(str)) {
	   return true;
	}else{
	  return false;
	}
};


/**
 * 判断是否为中文
 */
var isChinese = function(temp) 
{ 
var re=/[^\u4e00-\u9fa5]/; 
if(re.test(temp)) return false; 
return true; 
} 

