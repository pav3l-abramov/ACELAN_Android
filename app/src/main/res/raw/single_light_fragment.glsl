precision mediump float;

uniform vec3 u_LightPos;
uniform vec4 u_ambientColor;
uniform vec4 u_diffuseColor;
uniform vec4 u_specularColor;
const float specular_exp = 16.0;
varying vec3 v_Normal;
varying vec3 v_Position;

void main()
{
    vec3 lightPosNorm = normalize(u_LightPos);
    vec3 cameraDir = normalize(-v_Position);
    vec3 halfDir = normalize(lightPosNorm + cameraDir);
    float specular = pow(max(dot(halfDir, v_Normal), 0.0), specular_exp);
    float diffuse = max(dot(lightPosNorm, v_Normal), 0.0);
    gl_FragColor = u_ambientColor * (1.0 - diffuse) + u_diffuseColor * (diffuse - specular) + u_specularColor * specular;
}
