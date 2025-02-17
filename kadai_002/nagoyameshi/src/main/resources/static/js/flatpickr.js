let minDate = new Date();
minDate.setHours(minDate.getHours() + 3); // 現在日時から3時間後に設定

let maxDate = new Date();
maxDate.setMonth(maxDate.getMonth() + 3); // 現在日時から3ヶ月後に設定

flatpickr('#commingDate', {
    locale: 'ja',
    minDate: minDate, // 3時間後をminDateに設定
    maxDate: maxDate, // 3ヶ月後をmaxDateに設定
});