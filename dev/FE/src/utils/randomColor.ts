import randomColor from 'randomcolor'

const getColors = (count : number) : string[] => randomColor({ count })
const getLightColors = (count : number) : string[] => randomColor({    luminosity: 'light',count })
const getDarkColors = (count : number) : string[] => randomColor({    luminosity: 'dark',count })

export {getColors, getLightColors, getDarkColors}
