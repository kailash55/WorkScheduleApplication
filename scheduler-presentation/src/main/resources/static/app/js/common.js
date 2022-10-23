function formatNumber(number)
{
	let formattedNumber = number.toLocaleString('en-US', {
	    minimumIntegerDigits: 2,
	    useGrouping: false
  	})
  	return formattedNumber
}

function getDayPeriod(hour)
{
	return hour>=12 ? "pm":"am"
}