set terminal pdfcairo enhanced color size 29cm,20cm font "Times-New-Roman"  fontscale 0.8
set output "FI-fundamental.pdf"
set title "Fundamental Diagram ({/:Italic v}_{max}=3)"
set xlabel "{/Symbol r}"
set ylabel "{/:Italic q}"
set yrange [0:1]
set ytic 0.2
set xtic 0.2
f(x) = (x<1./4.)? 3*x : 1-x
plot "Flow-output.txt" pt 7 ps 2 t "simulation", f(x) lw 3 t "thoery"