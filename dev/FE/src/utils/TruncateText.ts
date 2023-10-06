const TruncateText = (text: string, maxLength: number) => {
  if (text.length <= maxLength) {
    return text;
  }

  const truncatedText = text.substring(0, maxLength) + '...';

  return truncatedText;
};
export default TruncateText;
