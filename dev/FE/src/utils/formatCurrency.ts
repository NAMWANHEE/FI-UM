// 숫자에 쉼표 붙여주는 함수
export const formatCurrency = (value: string): string => {
  const num = parseInt(value, 10);
  return num.toLocaleString('en-US');
};
