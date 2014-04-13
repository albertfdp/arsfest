#!/usr/bin/python

import math

# NE, NW, SE, SW - Insert NE, NW and SE[Y]
my_bounds = [ {'X':12.523055,'Y':55.787554}, {'X':12.526623,'Y':55.786939}, {'X':0,'Y':55.785849}, {'X':0,'Y':0} ]

def generate_coordinates():

    #generate coordinates of the TOP line
    a_top = ( my_bounds[0]['Y'] - 
              my_bounds[1]['Y'] ) / ( my_bounds[0]['X'] - 
                                      my_bounds[1]['X'] )

    b_top = ( my_bounds[0]['Y'] - 
              a_top * my_bounds[0]['X'] )

    print "Top line equation: y=%sx+%s" % (a_top, b_top)

    #generate coordinates of the LEFT line
    a_left = ( -1 ) * ( 1 / a_top )
    b_left = ( my_bounds[0]['Y'] - a_left * my_bounds[0]['X'] )

    print "Left line equation: y=%sx+%s" % (a_left, b_left)

    #generate lengths

    my_bounds[2]['X'] = ( my_bounds[2]['Y'] - b_left ) / a_left

    length_top_down = math.sqrt(
        pow((my_bounds[0]['X']-my_bounds[2]['X']), 2) +
        pow((my_bounds[0]['Y']-my_bounds[2]['Y']), 2)
        )
    length_left_right = math.sqrt(
        pow((my_bounds[0]['X']-my_bounds[1]['X']), 2) +
        pow((my_bounds[0]['Y']-my_bounds[1]['Y']), 2)
        )


    print "Top-down length is %s" % (length_top_down)
    print "Left-right length is %s" % (length_left_right)


generate_coordinates()
