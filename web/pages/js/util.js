/**
 * 验证函数，默认进行非空验证，如果有其他逻辑，请实现验证器的逻辑
 * 
 * @param value
 *            待验证的值
 * @param validator
 *            验证器
 */
function validate(value, validator) {
	if (!validator) {
		return isEmpty(value);
	}
};
/**
 * 验证值是否为空
 * 
 * @param str
 */
function isEmpty(str) {
	return str == undefined || str == null || str == '';
};