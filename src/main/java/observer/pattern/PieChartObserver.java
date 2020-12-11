package observer.pattern;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JSlider;

import observer.CourseRecord;
import observer.LayoutConstants;

/**
 * This class represents a pie chart view of a vector of data. Uses the Observer
 * pattern.
 */
public class PieChartObserver extends JPanel implements Observer {
	
	private Vector<CourseRecord> courseData;
	
	/**
	 * Creates a BarChartObserver object
	 * 
	 * @param data
	 *            a CourseData object to observe
	 */
	public PieChartObserver(CourseData data) {
		data.attach(this);
		this.courseData = (Vector<CourseRecord>) data.getUpdate();
		this.setPreferredSize(new Dimension(2 * LayoutConstants.xOffset
				+ (LayoutConstants.barSpacing + LayoutConstants.barWidth)
				* this.courseData.size(), LayoutConstants.graphHeight + 2
				* LayoutConstants.yOffset));
		this.setBackground(Color.white);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		ArrayList<Integer> dataA = new ArrayList<Integer>();
		for(CourseRecord course : courseData)
			dataA.add(course.getNumOfStudents());
		Integer[] data = new Integer[dataA.size()];
		for(int i=0; i<dataA.size(); i++)
			data[i] = dataA.get(i);
		paintPieChart(g, data);
	}
	
	/**
	 * Paint pie method
	 * 
	 * @param g, data
	 *            a Graphics object on which to paint
	 */
	public void paintPieChart(Graphics g, Integer[] data) {
		super.paint(g);
        
        Rectangle area = this.getBounds();

		//first compute the total number of students
		double total = 0.0;
		for (int i = 0; i < data.length; i++) {
			total += data[i];
		}
		//if total == 0 nothing to draw
		if (total != 0) { 
	        double curValue = 0.0D;
			int startAngle = 0;
			for (int i = 0; i < courseData.size(); i++) {
	             startAngle = (int) (curValue * 360 / total);
	             int arcAngle = (int) (courseData.elementAt(i).getNumOfStudents() * 360 / total);
	             //draw the arc
	             g.setColor(LayoutConstants.courseColours[i]);
	             g.fillArc(area.width/2-area.height/2, area.y, area.height, area.height, startAngle, arcAngle);
	             curValue += courseData.elementAt(i).getNumOfStudents();
	          }
		}
	}

	/**
	 * Informs this observer that the observed CourseData object has changed
	 * 
	 * @param o
	 *            the observed CourseData object that has changed
	 */
	@Override
	public void update(Observable o) {
		 CourseData data = (CourseData) o;
	        this.courseData = data.getUpdate();

	        this.revalidate();
	        this.repaint();
	}
}