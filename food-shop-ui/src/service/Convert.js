export function convertNameForUrl(name){
    if(name)
        var name = name.replaceAll('/', '-');
        name = name.replaceAll(' ', '-');
    return name;
}
export function nonBreakingSpace(name){
    if(name)
        var name = name.replaceAll(' ', '\u00A0');
    return name;
}
export function ceilRound(value){
    return Number(value.toFixed(2));
}