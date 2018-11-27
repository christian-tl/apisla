var hostMemMap = {
    "10.4.13.25" : 8,
    "10.4.12.26" : 8,
    "10.4.12.27" : 8,
    "10.4.12.28" : 8,
    "10.4.13.27" : 8,
    "10.4.13.28" : 8,
    "10.3.16.57" : 8,
    "10.3.17.58" : 8,
    "10.3.17.57" : 8,
    "10.3.17.41" : 8,
    "10.3.17.44" : 8,
    "10.3.16.46" : 8,
    "10.3.16.44" : 8,
    "10.3.17.46" : 8,
    "10.3.17.45" : 8,
    "10.3.17.43" : 8,
    "10.3.16.45" : 8,
    "10.4.13.130": 16,
    "10.4.12.130": 16,
    "10.4.15.110": 32,
    "10.4.18.122": 32,
    "10.4.18.123": 32,
    "10.4.19.124": 32,
    "10.4.19.122": 32,
    "10.4.19.123": 32,
    "10.4.18.124": 32,
    "10.4.14.107": 32,
    "10.4.14.111": 32,
    "10.4.15.108": 32,
    "10.4.15.109": 32,
    "10.4.19.125": 32,
    "10.4.18.125": 32,
    "10.4.19.159": 32,
    "10.4.18.159": 32,
    "10.4.18.113": 32,
    "10.4.19.113": 32,
    "10.4.15.52":  32,
    "10.4.15.53":  32,
    "10.4.15.54":  32,
    "10.4.15.55":  32,
    "10.4.15.56":  32,
    "10.4.15.57":  32,
    "10.4.15.58":  32
};

function getShowColor(ip){
    var color;
    var mem = hostMemMap[ip];
    switch (mem){
        case 8:
            color = "#000000";
            break;
        case 16:
            color = "#02CBC4";
            break;
        case 32:
            color = "#0245CB";
            break;
        default:
            color = "#000000";
            break;
    }
    return color;
}