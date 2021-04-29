function generatePDF (){
    const element = document.getElementById("timeReport");

    var opt = {
        margin:       [0.5,0.5],
        filename:     'time_report.pdf',
        pageBreak:    { mode:['avoid-all', 'css']},
        image:        { type: 'jpeg', quality: 0.98 },
        html2canvas:  { scale: 2 },
        jsPDF:        { unit: 'in', format: 'letter', orientation: 'portrait' }
    };

    html2pdf().set(opt).from(element).save();
}