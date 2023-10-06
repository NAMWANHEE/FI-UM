const option = {
  maximumFractionDigits: 0,
};

const priceFilter = (price?: number | null): string => {
  if (!price) return '0원';
  return price.toLocaleString('ko-KR', option) + '원';
};

const priceFilterPlus = (price?: number | null): string => {
  if (!price) return '0원';
  const res = price.toLocaleString('ko-KR', option) + '원';
  return price > 0 ? `+${res}` : res;
};

const countFilter = (count?: number | null): string => {
  if (!count) return '0개';
  return count.toLocaleString('ko-KR', option) + '개';
};

const ratioFilter = (price1: number, price2: number): string => {
  return Math.round((price1 / price2) * 100) + '%';
};

export default priceFilter;
export { priceFilterPlus, countFilter, ratioFilter };
