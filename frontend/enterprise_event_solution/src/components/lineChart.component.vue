

<script lang="ts">

export default{
  components:{
   
  },
    name: "LineChart",  
    data(){
        return{
            chart_data : [{
  "iphone": 123,
  "android": 208,
  "when": "2019-08-01"
},{
  "iphone": 165,
  "android": 201,
  "when": "2019-09-01"
}],
chart_config : {
  date: {
    key: false,
    inputFormat: "%Y-%m-%d",
    outputFormat: "%Y-%m-%d",
  },
  values: false,
  axis: {
    yTitle: false,
    xTitle: false,
    yFormat: ".0f",
    xFormat: "%Y-%m-%d",
    yTicks: 5,
    xTicks: 3
  },
  color: {
    key: false,
    keys: false,
    scheme: false,
    current: "#1f77b4",
    default: "#AAA",
    axis: "#000",
  },
  curve: "curveLinear",
  margin: {
    top: 20,
    right: 20,
    bottom: 20,
    left: 40
  },
  points: {
    visibleSize: 3,
    hoverSize: 6,
  },
  tooltip: {
    labels: false,
  },
  transition: {
    duration: 350,
    ease: "easeLinear",
  },
}
        }
    }
}





</script>

<style>

</style>