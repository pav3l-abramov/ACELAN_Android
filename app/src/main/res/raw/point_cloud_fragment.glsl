precision mediump float;

uniform vec4 u_ambientColor;
varying vec4 v_Color;

void main()
{
    float maxDepth = 50.0;
    float depth = gl_FragCoord.z / gl_FragCoord.w;
    //gl_FragColor = vec4(u_ambientColor * (maxDepth / depth), 1.0);
    gl_FragColor = v_Color;
}
