module.exports.ConvertToLocalDateTime = function ConvertToLocalDateTime(convertDate){
    const date = new Date(convertDate);
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    const hour = date.getHours().toString().padStart(2, '0');
    const minute = date.getMinutes().toString().padStart(2, '0');
    const second = date.getSeconds().toString().padStart(2, '0');

    return `${year}-${month}-${day}T${hour}:${minute}:${second}`;
}

module.exports.FormatPrettyLocalDateTime = function FormatPrettyLocalDateTime(convertDate){
    const datetime = new Date(convertDate);
    const month = datetime.getMonth() + 1;
    const day = datetime.getDate();
    const year = datetime.getFullYear();
    const hours = datetime.getHours();
    const minutes = datetime.getMinutes();
    const amOrPm = hours >= 12 ? 'PM' : 'AM';

    return `${month.toString().padStart(2, '0')}/${day.toString().padStart(2, '0')}/${year} ${hours % 12}:${minutes.toString().padStart(2, '0')} ${amOrPm}`;

}

module.exports.CalculateAge = function CalculateAge(date){
    const currentDate = new Date();
    const birthDate = new Date(date);
    const birthYear = birthDate.getFullYear();
    const birthMonth = birthDate.getMonth();
    const birthDay = birthDate.getDate();
    const currentYear = currentDate.getFullYear();
    const currentMonth = currentDate.getMonth();
    const currentDay = currentDate.getDate();

    let ageInYears = currentYear - birthYear;
    let ageInMonths = currentMonth - birthMonth;

    if (currentMonth < birthMonth || (currentMonth === birthMonth && currentDay < birthDay)) {
        ageInYears--;
        ageInMonths += 12;
    }

    return `${ageInYears > 0 ? `${ageInYears} years, ` : ''}${ageInMonths > 0 ? `${ageInMonths} months` : ''}`;
}