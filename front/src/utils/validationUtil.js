/** 이름 검사: 한글, 영어, 숫자 2글자 이상 10글자 이하 */
export const validateName = (name) =>
  /^(?=.*[가-힣a-zA-Z])[가-힣a-zA-Z\d]{2,10}$/.test(name);

/** 내용 검사: 공백포함 10글자 이상 */
export const validateContent = (text) => text.trim().length >= 10;

/** 숫자 검사: 오로지 숫자만, 음수거름 */
export const validateOnlyNonNegativeNumbers = (text) =>
  /^\d+$/.test(text) && Number(text) >= 0;

/**  */
