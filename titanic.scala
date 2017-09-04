import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.PairDStreamFunctions
object sca_j {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("sca_j").setMaster("local") //local mode
    val sc = new SparkContext(conf)
    val data = sc.textFile("titanic.txt").map(_.split("\t"))
      .filter(lines => lines(0) != "Name")
      .map(tdata => Titanic(tdata(0), tdata(1), tdata(2), tdata(3), tdata(4)))
    //val f=  data.filter(_.Age != "NA").filter(_.Sex=="NA").filter(_.Sex=="1")
    val sorted=data.sortBy(_.Age) 
    val f=  sorted.filter(_.Age != "NA")
    val p=f.filter(_.Sex!="NA").filter(_.Sex!="40")
    val dataBysex: RDD[(String, Titanic)] = p.keyBy{ x => x.Sex}
    val sexcount=dataBysex.countByKey()map(x => x._1 + " = " + x._2)
    val h=  p.filter(_.Survived != "1")
    val m = h.keyBy(x => (
                      if(x.Age.toDouble >= 0.00 & x.Age.toDouble <= 10.99 ) "0-10" 
               else if(x.Age.toDouble >= 11.00 & x.Age.toDouble <= 20.99 ) "11-20" 
               else if(x.Age.toDouble >= 21.00 & x.Age.toDouble <= 30.99 ) "21-30" 
               else if(x.Age.toDouble >= 31.00 & x.Age.toDouble <= 40.99 ) "31-40"    
               else if(x.Age.toDouble >= 41.00 & x.Age.toDouble <= 50.99 ) "41-50"
               else if(x.Age.toDouble >= 51.00 & x.Age.toDouble <= 60.99 ) "51-60"
               else if(x.Age.toDouble >= 61.00 & x.Age.toDouble <= 70.99 ) "61-70"
               else if(x.Age.toDouble >= 71.00 & x.Age.toDouble <= 80.99 ) "71-80"
               else if(x.Age.toDouble >= 81.00 & x.Age.toDouble <= 90.99 ) "81-90"
                        else "91-100"))
    val SurvivedBysex: RDD[(String, Titanic)] = h.keyBy{ x => x.Sex}
    val SurvivedByage: RDD[(String, Titanic)] = h.keyBy{ x => x.Age}
    val agerange=SurvivedByage.countByKey()map(x => x._1 + " = " + x._2)
    val personssurvived=SurvivedBysex.countByKey()map(x => x._1 + " = " + x._2)
    val personssurvivedagewise=m.countByKey()map(x => x._1 + " = " + x._2)
    //val k=personssurvivedagewise
   println("No. of males and females in titanic")
   sexcount.foreach(println)
   println("No. of males and females survived in titanic")
   personssurvived.foreach(println)
   println("No. of persons survived in titanic as per thier age range")
   personssurvivedagewise.foreach(println)
}
}