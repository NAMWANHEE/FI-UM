const snakeToTitle = (snake: string) => {
  return snake
    .split('_')
    .map((substr) => substr.charAt(0).toUpperCase() + substr.slice(1))
    .join(' ');
};

export default snakeToTitle;
