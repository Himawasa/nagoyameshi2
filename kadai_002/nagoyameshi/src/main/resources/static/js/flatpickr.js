let maxDate = new Date();
maxDate = maxDate.setMonth(maxDate.getMonth() + 3);

flatpickr('#commingDate', {
  locale: 'ja',
  minDate: 'today',
  maxDate: maxDate
});