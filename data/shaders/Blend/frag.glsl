#ifdef GL_ES
precision highp float;
#endif

uniform sampler2D texture;
uniform sampler2D texture2;
uniform vec2 sketchSize;


void main(void) {
    vec2 uv = gl_FragCoord.xy / sketchSize.xy;
    vec4 color = texture2D(texture, uv.xy);
    vec4 color2 = texture2D(texture2, vec2(uv.x, 1-uv.y));

    // Blend the two textures based on component of color 1
    color = mix(color, color2, color.r);

    gl_FragColor = color;
}
