export default interface graphDataType {
	"timeIndex": String[],
	"temperatureData": number[],
	"dustData": number[],
	"humidityData": number[],
	"poseTimeIndex": number[],
	"poseData": String[],
	"poseTimeSum": {
			"sleep":number,
			"away": number,
			"bad": number,
			"phone": number,
			"good": number
	},
	"start": number,
	"end": number
}